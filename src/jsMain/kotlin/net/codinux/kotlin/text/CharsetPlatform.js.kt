package net.codinux.kotlin.text

import net.codinux.kotlin.lang.ByteArrayBuilder
import org.khronos.webgl.ArrayBufferView
import org.khronos.webgl.Int8Array
import org.khronos.webgl.Uint8Array


external class TextDecoder(charset: String) {
    val encoding: String
    fun decode(data: ArrayBufferView): String
}

external class TextEncoder(charset: String) {
    val encoding: String
    fun encode(data: String): Uint8Array
}

internal actual object CharsetPlatform {

    actual fun forName(charsetName: String): Charset? = try {
        val decoder = TextDecoder(charsetName)
        val encoder = TextEncoder(charsetName)

        JsCharset(charsetName, encoder, decoder)
    } catch (ignored: dynamic) {
        null
    }

}

class JsCharset(name: String, private val encoder: TextEncoder, private val decoder: TextDecoder) : Charset(name) {

    constructor(encoder: TextEncoder, decoder: TextDecoder) : this(decoder.encoding, encoder, decoder)

    // or encoder.encoding == "utf-8", as on JavaScript for encoding only UTF-8 is supported (https://developer.mozilla.org/en-US/docs/Web/API/TextEncoder)
    override val canEncode = encoder.encoding == decoder.encoding

    override fun encode(out: ByteArrayBuilder, src: CharSequence, start: Int, end: Int) {
        if (canEncode == false) {
            throw UnsupportedOperationException("${decoder.encoding}' does not support encoding")
        }

        out.append(toByteArray(encoder.encode(src.substring(start, end))))
    }

    private fun toByteArray(uint8Array: Uint8Array): ByteArray =
        Int8Array(uint8Array.unsafeCast<Int8Array>()).unsafeCast<ByteArray>()

    override fun decode(out: StringBuilder, src: ByteArray, start: Int, end: Int): Int {
        out.append(decoder.decode(src.unsafeCast<Uint8Array>().subarray(start, end)))
        // @TODO: This charset won't support partial characters.
        return end - start
    }

    override fun equals(other: Any?): Boolean = other is JsCharset && this.name == other.name
    override fun hashCode(): Int = name.hashCode()
    override fun toString(): String = name

}