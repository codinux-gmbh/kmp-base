package net.codinux.kotlin.text

import net.codinux.collections.toImmutableMap
import net.codinux.kotlin.lang.*

// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/korio/src/commonMain/kotlin/korlibs/io/lang/Charset.kt

abstract class Charset(val name: String) {

    // Just an estimation, might not be accurate, but hopefully will help setting StringBuilder and ByteArrayBuilder to a better initial capacity
    open fun estimateNumberOfCharactersForBytes(nbytes: Int): Int = nbytes * 2
    open fun estimateNumberOfBytesForCharacters(nchars: Int): Int = nchars * 2

    /**
     * Not all Encodings support encoding, e.g.
     * - on JavaScript only UTF-8 is supported for encodings (see e.g. https://developer.mozilla.org/en-US/docs/Web/API/TextEncoder)
     * - on JVM for "special-purpose auto-detect charsets whose decoders can determine which of several possible encoding
     * schemes is in use by examining the input byte sequence. Such charsets do not support encoding because there is
     * no way to determine which encoding should be used on output."
     */
    open val canEncode = true

    val isUnicodeCharset = name.startsWith("UTF-", true)

	abstract fun encode(out: ByteArrayBuilder, src: CharSequence, start: Int = 0, end: Int = src.length)

    /**
     * Decodes the [src] [ByteArray] [start]-[end] range using this [Charset]
     * and writes the result into the [out] [StringBuilder].
     *
     * Returns the number of consumed bytes ([end]-[start] if not under-flowing) and less if a character is not complete.
     **/
    abstract fun decode(out: StringBuilder, src: ByteArray, start: Int = 0, end: Int = src.size): Int

    override fun toString() = name

	companion object {

        private val platformCharsets by lazy { CharsetPlatform.availableCharsets }

        val availableCharsets by lazy {
            val charsets = platformCharsets.toMutableMap()

            // ensure platform specific implementation of standard Charsets gets replaced by our implementation (TODO: sensful?)
            Charsets.StandardCharsets.forEach { (name, charset) ->
                charsets[name] = charset
            }

            charsets.toImmutableMap()
        }

        fun forName(charsetName: String): Charset? {
            val normalizedName = charsetName.uppercase().replace("_", "").replace("-", "")
            when (normalizedName) {
                "ASCII", "USASCII" -> return Charsets.US_ASCII
                "UTF8" -> return Charsets.UTF8
                "UTF16", "UTF16LE" -> return Charsets.UTF16_LE
                "UTF16BE" -> return Charsets.UTF16_BE
                "ISO88591", "LATIN1" -> return Charsets.ISO_8859_1
            }

            return CharsetPlatform.forName(normalizedName)
                ?: CharsetPlatform.forName(charsetName)
		}

        fun StringBuilder.appendCodePointV(codePoint: Int) {
            if (codePoint in 0xD800..0xDFFF || codePoint > 0xFFFF) {
                val U0 = codePoint - 0x10000
                val hs = U0.extract(10, 10)
                val ls = U0.extract(0, 10)
                append(((0b110110 shl 10) or (hs)).toChar())
                append(((0b110111 shl 10) or (ls)).toChar())
            } else {
                append(codePoint.toChar())
            }
        }

        inline fun decodeCodePoints(src: CharSequence, start: Int, end: Int, block: (codePoint: Int) -> Unit) {
            var highSurrogate = 0
            loop@for (n in start until end) {
                val char = src[n].code
                val codePoint = if (char in 0xD800..0xDFFF) {
                    when (char.extract(10, 6)) {
                        0b110110 -> {
                            highSurrogate = char and 0x3FF
                            continue@loop
                        }
                        0b110111 -> {
                            0x10000 + ((highSurrogate shl 10) or (char and 0x3FF))
                        }
                        else -> error("Unknown $char")
                    }
                } else {
                    char
                }
                block(codePoint)
            }
        }
	}
}

open class UTC8CharsetBase(name: String) : Charset(name) {
    override fun estimateNumberOfCharactersForBytes(nbytes: Int): Int = nbytes * 2
    override fun estimateNumberOfBytesForCharacters(nchars: Int): Int = nchars * 2

    private fun createByte(codePoint: Int, shift: Int): Int = codePoint shr shift and 0x3F or 0x80

	override fun encode(out: ByteArrayBuilder, src: CharSequence, start: Int, end: Int) {
        decodeCodePoints(src, start, end) { codePoint ->
            if (codePoint and 0x7F.inv() == 0) { // 1-byte sequence
                out.append(codePoint.toByte())
            } else {
                when {
                    codePoint and 0x7FF.inv() == 0 -> // 2-byte sequence
                        out.append((codePoint shr 6 and 0x1F or 0xC0).toByte())
                    codePoint and 0xFFFF.inv() == 0 -> { // 3-byte sequence
                        out.append((codePoint shr 12 and 0x0F or 0xE0).toByte())
                        out.append((createByte(codePoint, 6)).toByte())
                    }
                    codePoint and -0x200000 == 0 -> { // 4-byte sequence
                        out.append((codePoint shr 18 and 0x07 or 0xF0).toByte())
                        out.append((createByte(codePoint, 12)).toByte())
                        out.append((createByte(codePoint, 6)).toByte())
                    }
                }
                out.append((codePoint and 0x3F or 0x80).toByte())
            }
        }
	}

	override fun decode(out: StringBuilder, src: ByteArray, start: Int, end: Int): Int {
		if ((start < 0 || start > src.size) || (end < 0 || end > src.size)) error("Out of bounds")
		var i = start
		loop@while (i < end) {
			val c = src[i].toInt() and 0xFF

            when (c shr 4) {
                in 0b0000..0b0111 -> {
                    // 0xxxxxxx
                    out.appendCodePointV(c)
                    i += 1
                }
                in 0b1100..0b1101 -> {
                    // 110x xxxx   10xx xxxx
                    if (i + 1 >= end) break@loop
                    out.appendCodePointV((c and 0x1F shl 6 or (src[i + 1].toInt() and 0x3F)))
                    i += 2
                }
                0b1110 -> {
                    // 1110 xxxx  10xx xxxx  10xx xxxx
                    if (i + 2 >= end) break@loop
                    out.appendCodePointV((c and 0x0F shl 12 or (src[i + 1].toInt() and 0x3F shl 6) or (src[i + 2].toInt() and 0x3F)))
                    i += 3
                }
                0b1111 -> {
                    // 1111 0xxx 10xx xxxx  10xx xxxx  10xx xxxx
                    if (i + 3 >= end) break@loop
                    out.appendCodePointV(0
                        .insert(src[i + 0].toInt().extract(0, 3), 18, 3)
                        .insert(src[i + 1].toInt().extract(0, 6), 12, 6)
                        .insert(src[i + 2].toInt().extract(0, 6), 6, 6)
                        .insert(src[i + 3].toInt().extract(0, 6), 0, 6)
                    )
                    i += 4

                }
                else -> {
                    out.append('\uFFFD')
                    i += 1
                    //TODO("${c shr 4}")
                }
            }
		}
        return i - start
	}
}

abstract class BaseSingleByteCharset(name: String) : Charset(name) {
    override fun estimateNumberOfCharactersForBytes(nbytes: Int): Int = nbytes
    override fun estimateNumberOfBytesForCharacters(nchars: Int): Int = nchars
}

open class SingleByteCharset(name: String, private val supportedChars: List<Char>) : BaseSingleByteCharset(name) {

	override fun encode(out: ByteArrayBuilder, src: CharSequence, start: Int, end: Int) {
		for (n in start until end) {
            val char = src[n]
            out.append(if (supportedChars.contains(char)) char.code else '?'.code)
		}
	}

	override fun decode(out: StringBuilder, src: ByteArray, start: Int, end: Int): Int {
		for (n in start until end) {
            out.append(supportedChars[src[n].toInt() and 0xFF])
		}
        return end - start
	}
}

class UTF16Charset(val le: Boolean) : Charset("UTF-16-" + (if (le) "LE" else "BE")) {
    override fun estimateNumberOfCharactersForBytes(nbytes: Int): Int = nbytes * 2
    override fun estimateNumberOfBytesForCharacters(nchars: Int): Int = nchars * 2

	override fun decode(out: StringBuilder, src: ByteArray, start: Int, end: Int): Int {
        var consumed = 0
		for (n in start until end step 2) {
		    val char = src.readS16(n, le).toChar()
		    out.append(char)
            consumed += 2
        }
        return consumed
	}

	override fun encode(out: ByteArrayBuilder, src: CharSequence, start: Int, end: Int) {
		val temp = ByteArray(2)
		for (n in start until end) {
			temp.write16(0, src[n].code, le)
			out.append(temp)
		}
	}
}

fun String.toByteArray(charset: Charset = Charsets.UTF8, start: Int = 0, end: Int = this.length): ByteArray {
	val out = ByteArrayBuilder(charset.estimateNumberOfBytesForCharacters(end - start))
	charset.encode(out, this, start, end)
	return out.toByteArray()
}

fun ByteArray.toString(charset: Charset = Charsets.UTF8, start: Int = 0, end: Int = this.size): String {
	val out = StringBuilder(charset.estimateNumberOfCharactersForBytes(end - start))
	charset.decode(out, this, start, end)
	return out.toString()
}

//fun ByteArray.readStringz(offset: Int, size: Int, charset: Charset = Charsets.UTF8): String {
//	var idx = offset
//	val stop = min(this.size, offset + size)
//	while (idx < stop) {
//		if (this[idx] == 0.toByte()) break
//		idx++
//	}
//	return this.copyOfRange(offset, idx).toString(charset)
//}
//
//fun ByteArray.readStringz(offset: Int, charset: Charset = Charsets.UTF8): String {
//	return readStringz(offset, size - offset, charset)
//}
//
//fun ByteArray.readString(offset: Int, size: Int, charset: Charset = Charsets.UTF8): String {
//	return this.copyOfRange(offset, offset + size).toString(charset)
//}
