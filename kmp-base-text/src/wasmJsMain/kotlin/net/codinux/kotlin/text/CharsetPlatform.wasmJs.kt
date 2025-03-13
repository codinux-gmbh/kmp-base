package net.codinux.kotlin.text

import net.codinux.kotlin.lang.ByteArrayBuilder
import net.codinux.kotlin.lang.toByteArray
import net.codinux.kotlin.lang.toUint8Array
import org.khronos.webgl.ArrayBufferView
import org.khronos.webgl.Uint8Array


external class TextDecoder(charset: String) {
    val encoding: String
    fun decode(data: ArrayBufferView): String
}

external class TextEncoder(charset: String) {
    val encoding: String
    fun encode(data: String): Uint8Array
}

// Calling TextDecoder with an unsupported charsetName results in a JS RangeError, which cannot be caught with a Kotlin
// try-catch clause (in Kotlin/JS we can use `catch(e: dynamic)`, but we cannot do that on Kotlin/WasmJS).
// So create TextDecoder in JS and catch errors there:
internal fun createTextDecoder(charsetName: String): TextDecoder? =
    js("""{ try {
        return new TextDecoder(charsetName);
    } catch (e) {
        console.log("Could not create TextDecoder for charset", charsetName, e)
        return null;
    } }""")


internal actual object CharsetPlatform {

    /**
     * All encodings known to JavaScripts and their labels, see https://encoding.spec.whatwg.org/#concept-encoding-get.
     */
    val JavaScriptEncodingsAndTheirLabels = mapOf(
        "UTF-8" to listOf("unicode-1-1-utf-8", "unicode11utf8", "unicode20utf8", "utf-8", "utf8", "x-unicode20utf8"),
        // Legacy single-byte encodings
        "IBM866" to listOf("866", "cp866", "csibm866", "ibm866"),
        "ISO-8859-2" to listOf("csisolatin2", "iso-8859-2", "iso-ir-101", "iso8859-2", "iso88592", "iso_8859-2", "iso_8859-2:1987", "l2", "latin2"),
        "ISO-8859-3" to listOf("csisolatin3", "iso-8859-3", "iso-ir-109", "iso8859-3", "iso88593", "iso_8859-3", "iso_8859-3:1988", "l3", "latin3"),
        "ISO-8859-4" to listOf("csisolatin4", "iso-8859-4", "iso-ir-110", "iso8859-4", "iso88594", "iso_8859-4", "iso_8859-4:1988", "l4", "latin4"),
        "ISO-8859-5" to listOf("csisolatincyrillic", "cyrillic", "iso-8859-5", "iso-ir-144", "iso8859-5", "iso88595", "iso_8859-5", "iso_8859-5:1988"),
        "ISO-8859-6" to listOf("arabic", "asmo-708", "csiso88596e", "csiso88596i", "csisolatinarabic", "ecma-114", "iso-8859-6", "iso-8859-6-e", "iso-8859-6-i", "iso-ir-127", "iso8859-6", "iso88596", "iso_8859-6", "iso_8859-6:1987"),
        "ISO-8859-7" to listOf("csisolatingreek", "ecma-118", "elot_928", "greek", "greek8", "iso-8859-7", "iso-ir-126", "iso8859-7", "iso88597", "iso_8859-7", "iso_8859-7:1987", "sun_eu_greek"),
        "ISO-8859-8" to listOf("csiso88598e", "csisolatinhebrew", "hebrew", "iso-8859-8", "iso-8859-8-e", "iso-ir-138", "iso8859-8", "iso88598", "iso_8859-8", "iso_8859-8:1988", "visual"),
        "ISO-8859-8-I" to listOf("csiso88598i", "iso-8859-8-i","logical"),
        "ISO-8859-10" to listOf("csisolatin6", "iso-8859-10", "iso-ir-157", "iso8859-10", "iso885910", "l6", "latin6"),
        "ISO-8859-13" to listOf("iso-8859-13", "iso8859-13", "iso885913"),
        "ISO-8859-14" to listOf("iso-8859-14", "iso8859-14", "iso885914"),
        "ISO-8859-15" to listOf("csisolatin9", "iso-8859-15", "iso8859-15", "iso885915", "iso_8859-15", "l9"),
        "ISO-8859-16" to listOf("iso-8859-16"),
        "KOI8-R" to listOf("cskoi8r", "koi", "koi8", "koi8-r", "koi8_r"),
        "KOI8-U" to listOf("koi8-ru", "koi8-u"),
        "macintosh" to listOf("csmacintosh", "mac", "macintosh", "x-mac-roman"),
        "windows-874" to listOf("dos-874", "iso-8859-11", "iso8859-11", "iso885911", "tis-620", "windows-874"),
        "windows-1250" to listOf("cp1250", "windows-1250", "x-cp1250"),
        "windows-1251" to listOf("cp1251", "windows-1251", "x-cp1251"),
        "windows-1252" to listOf("ansi_x3.4-1968", "ascii", "cp1252", "cp819", "csisolatin1", "ibm819", "iso-8859-1", "iso-ir-100", "iso8859-1", "iso88591", "iso_8859-1", "iso_8859-1:1987", "l1", "latin1", "us-ascii", "windows-1252", "x-cp1252"),
        "windows-1253" to listOf("cp1253", "windows-1253", "x-cp1253"),
        "windows-1254" to listOf("cp1254", "csisolatin5", "iso-8859-9", "iso-ir-148", "iso8859-9", "iso88599", "iso_8859-9", "iso_8859-9:1989", "l5", "latin5", "windows-1254", "x-cp1254"),
        "windows-1255" to listOf("cp1255", "windows-1255", "x-cp1255"),
        "windows-1256" to listOf("cp1256", "windows-1256", "x-cp1256"),
        "windows-1257" to listOf("cp1257", "windows-1257", "x-cp1257"),
        "windows-1258" to listOf("cp1258", "windows-1258", "x-cp1258"),
        "x-mac-cyrillic" to listOf("x-mac-cyrillic", "x-mac-ukrainian"),
        // Legacy multi-byte Chinese (simplified) encodings
        "GBK" to listOf("chinese", "csgb2312", "csiso58gb231280", "gb2312", "gb_2312", "gb_2312-80", "gbk", "iso-ir-58", "x-gbk"),
        "gb18030" to listOf("gb18030"),
        // Legacy multi-byte Chinese (traditional) encodings
        "Big5" to listOf( 	"big5", "big5-hkscs", "cn-big5", "csbig5", "x-x-big5"),
        // Legacy multi-byte Japanese encodings
        "EUC-JP" to listOf("cseucpkdfmtjapanese", "euc-jp", "x-euc-jp"),
        "ISO-2022-JP" to listOf("csiso2022jp", "iso-2022-jp"),
        "Shift_JIS" to listOf("csshiftjis", "ms932", "ms_kanji", "shift-jis", "shift_jis", "sjis", "windows-31j", "x-sjis"),
        // Legacy multi-byte Korean encodings
        "EUC-KR" to listOf("cseuckr", "csksc56011987", "euc-kr", "iso-ir-149", "korean", "ks_c_5601-1987", "ks_c_5601-1989", "ksc5601", "ksc_5601", "windows-949"),
        // Legacy miscellaneous encodings
        "replacement" to listOf("csiso2022kr", "hz-gb-2312", "iso-2022-cn", "iso-2022-cn-ext", "iso-2022-kr", "replacement"), // in Kotlin 1.9 creating a TextDecoder with "replacement" throws an error (in Kotlin 2 it works)
        "UTF-16BE" to listOf("unicodefffe", "utf-16be"),
        "UTF-16LE" to listOf("csunicode", "iso-10646-ucs-2", "ucs-2", "unicode", "unicodefeff", "utf-16", "utf-16le"),
        "x-user-defined" to listOf("x-user-defined")
    )

    actual val availableCharsets: Map<String, Charset> by lazy {
        JavaScriptEncodingsAndTheirLabels.mapNotNull { (name, _) ->
            forName(name)?.let { name to it }
        }.toMap()
    }

    actual fun forName(charsetName: String): Charset? = try {
        val decoder = createTextDecoder(charsetName)
        if (decoder == null) {
            null
        } else {
            val encoder = TextEncoder(charsetName)

            JsCharset(charsetName, encoder, decoder)
        }
    } catch (ignored: Throwable) {
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
        uint8Array.toByteArray()

    override fun decode(out: StringBuilder, src: ByteArray, start: Int, end: Int): Int {
        out.append(decoder.decode(src.toUint8Array().subarray(start, end)))
        // @TODO: This charset won't support partial characters.
        return end - start
    }

    override fun equals(other: Any?): Boolean = other is JsCharset && this.name == other.name
    override fun hashCode(): Int = name.hashCode()
    override fun toString(): String = name

}