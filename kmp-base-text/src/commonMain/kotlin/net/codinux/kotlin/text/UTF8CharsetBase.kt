package net.codinux.kotlin.text

import net.codinux.kotlin.lang.ByteArrayBuilder

open class UTF8CharsetBase(name: String) : Charset(name) {

    override fun estimateNumberOfCharactersForBytes(nbytes: Int): Int = nbytes * 2
    override fun estimateNumberOfBytesForCharacters(nchars: Int): Int = nchars * 2

    protected open fun createByte(codePoint: Int, shift: Int): Int = codePoint shr shift and 0x3F or 0x80

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