package net.codinux.kotlin.text

import net.codinux.kotlin.lang.ByteArrayBuilder
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.CodingErrorAction

internal actual object CharsetPlatform {

    actual val availableCharsets: Map<String, Charset> by lazy {
        java.nio.charset.Charset.availableCharsets()
            .map { (name, charset) -> name to JvmCharset(name, charset) }
            .toMap()
    }

    actual fun forName(charsetName: String): Charset? = try {
        java.nio.charset.Charset.forName(charsetName)?.let { charset ->
            JvmCharset(charsetName, charset)
        }
    } catch (ignored: Exception) {
        null
    }

}

class JvmCharset(name: String, private val charset: java.nio.charset.Charset) : Charset(name) {

    private val decoder by lazy { charset.newDecoder().onMalformedInput(CodingErrorAction.IGNORE) }
    private val encoder by lazy { charset.newEncoder().onMalformedInput(CodingErrorAction.IGNORE) }

    override val canEncode = charset.canEncode()

    override fun encode(out: ByteArrayBuilder, src: CharSequence, start: Int, end: Int) {
        val bb = encoder.encode(CharBuffer.wrap(src, start, end))
        out.append(ByteArray(bb.remaining()).also { bb.get(it) })
    }

    override fun decode(out: StringBuilder, src: ByteArray, start: Int, end: Int): Int {
        val bb = ByteBuffer.wrap(src, start, end - start)
        out.append(decoder.decode(bb))
        return bb.position() - start
    }

    override fun equals(other: Any?): Boolean = other is JvmCharset && this.charset == other.charset
    override fun hashCode(): Int = charset.hashCode()
    override fun toString(): String = name

}