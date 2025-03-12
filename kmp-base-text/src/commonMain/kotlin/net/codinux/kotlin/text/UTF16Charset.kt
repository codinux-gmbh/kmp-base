package net.codinux.kotlin.text

import net.codinux.kotlin.lang.ByteArrayBuilder

open class UTF16Charset(val le: Boolean) : Charset("UTF-16-" + (if (le) "LE" else "BE")) {

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