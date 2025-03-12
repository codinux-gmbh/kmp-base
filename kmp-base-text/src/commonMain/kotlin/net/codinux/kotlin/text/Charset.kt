package net.codinux.kotlin.text

import net.codinux.kotlin.lang.ByteArrayBuilder

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

    fun encode(src: CharSequence, start: Int = 0, end: Int = src.length): ByteArray = ByteArrayBuilder().let { builder ->
        encode(builder, src, start, end)
        builder.toByteArray()
    }

    /**
     * Decodes the [src] [ByteArray] [start]-[end] range using this [Charset]
     * and writes the result into the [out] [StringBuilder].
     *
     * Returns the number of consumed bytes ([end]-[start] if not under-flowing) and less if a character is not complete.
     **/
    abstract fun decode(out: StringBuilder, src: ByteArray, start: Int = 0, end: Int = src.size): Int

    fun decode(src: ByteArray, start: Int = 0, end: Int = src.size): String = StringBuilder().let { builder ->
        decode(builder, src, start, end)
        builder.toString()
    }

    override fun toString() = name

	companion object {

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




fun String.toByteArray(charset: Charset /*= Charsets.UTF8*/, start: Int = 0, end: Int = this.length): ByteArray {
	val out = ByteArrayBuilder(charset.estimateNumberOfBytesForCharacters(end - start))
	charset.encode(out, this, start, end)
	return out.toByteArray()
}

fun ByteArray.toString(charset: Charset /*= Charsets.UTF8*/, start: Int = 0, end: Int = this.size): String {
	val out = StringBuilder(charset.estimateNumberOfCharactersForBytes(end - start))
	charset.decode(out, this, start, end)
	return out.toString()
}