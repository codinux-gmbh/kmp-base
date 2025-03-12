package net.codinux.kotlin.text

import net.codinux.kotlin.lang.ByteArrayBuilder

abstract class BaseSingleByteCharset(name: String) : Charset(name) {
    override fun estimateNumberOfCharactersForBytes(nbytes: Int): Int = nbytes
    override fun estimateNumberOfBytesForCharacters(nchars: Int): Int = nchars
}

open class SingleByteCharset(name: String, protected val supportedChars: List<Char>) : BaseSingleByteCharset(name) {

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