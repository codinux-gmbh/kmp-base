package net.codinux.kotlin.text

import net.codinux.collections.immutableListOf
import net.codinux.collections.immutableMapOf
import net.codinux.collections.toImmutableMap
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

    /**
     * All encodings known to JavaScripts and their labels, see https://encoding.spec.whatwg.org/#concept-encoding-get.
     */
    val JavaScriptEncodingsAndTheirLabels = immutableMapOf(
        "UTF-8" to immutableListOf("unicode-1-1-utf-8", "unicode11utf8", "unicode20utf8", "utf-8", "utf8", "x-unicode20utf8"),
        // Legacy single-byte encodings
        "IBM866" to immutableListOf("866", "cp866", "csibm866", "ibm866"),
        "ISO-8859-2" to immutableListOf("csisolatin2", "iso-8859-2", "iso-ir-101", "iso8859-2", "iso88592", "iso_8859-2", "iso_8859-2:1987", "l2", "latin2"),
        "ISO-8859-3" to immutableListOf("csisolatin3", "iso-8859-3", "iso-ir-109", "iso8859-3", "iso88593", "iso_8859-3", "iso_8859-3:1988", "l3", "latin3"),
        "ISO-8859-4" to immutableListOf("csisolatin4", "iso-8859-4", "iso-ir-110", "iso8859-4", "iso88594", "iso_8859-4", "iso_8859-4:1988", "l4", "latin4"),
        "ISO-8859-5" to immutableListOf("csisolatincyrillic", "cyrillic", "iso-8859-5", "iso-ir-144", "iso8859-5", "iso88595", "iso_8859-5", "iso_8859-5:1988"),
        "ISO-8859-6" to immutableListOf("arabic", "asmo-708", "csiso88596e", "csiso88596i", "csisolatinarabic", "ecma-114", "iso-8859-6", "iso-8859-6-e", "iso-8859-6-i", "iso-ir-127", "iso8859-6", "iso88596", "iso_8859-6", "iso_8859-6:1987"),
        "ISO-8859-7" to immutableListOf("csisolatingreek", "ecma-118", "elot_928", "greek", "greek8", "iso-8859-7", "iso-ir-126", "iso8859-7", "iso88597", "iso_8859-7", "iso_8859-7:1987", "sun_eu_greek"),
        "ISO-8859-8" to immutableListOf("csiso88598e", "csisolatinhebrew", "hebrew", "iso-8859-8", "iso-8859-8-e", "iso-ir-138", "iso8859-8", "iso88598", "iso_8859-8", "iso_8859-8:1988", "visual"),
        "ISO-8859-8-I" to immutableListOf("csiso88598i", "iso-8859-8-i","logical"),
        "ISO-8859-10" to immutableListOf("csisolatin6", "iso-8859-10", "iso-ir-157", "iso8859-10", "iso885910", "l6", "latin6"),
        "ISO-8859-13" to immutableListOf("iso-8859-13", "iso8859-13", "iso885913"),
        "ISO-8859-14" to immutableListOf("iso-8859-14", "iso8859-14", "iso885914"),
        "ISO-8859-15" to immutableListOf("csisolatin9", "iso-8859-15", "iso8859-15", "iso885915", "iso_8859-15", "l9"),
        "ISO-8859-16" to immutableListOf("iso-8859-16"),
        "KOI8-R" to immutableListOf("cskoi8r", "koi", "koi8", "koi8-r", "koi8_r"),
        "KOI8-U" to immutableListOf("koi8-ru", "koi8-u"),
        "macintosh" to immutableListOf("csmacintosh", "mac", "macintosh", "x-mac-roman"),
        "windows-874" to immutableListOf("dos-874", "iso-8859-11", "iso8859-11", "iso885911", "tis-620", "windows-874"),
        "windows-1250" to immutableListOf("cp1250", "windows-1250", "x-cp1250"),
        "windows-1251" to immutableListOf("cp1251", "windows-1251", "x-cp1251"),
        "windows-1252" to immutableListOf("ansi_x3.4-1968", "ascii", "cp1252", "cp819", "csisolatin1", "ibm819", "iso-8859-1", "iso-ir-100", "iso8859-1", "iso88591", "iso_8859-1", "iso_8859-1:1987", "l1", "latin1", "us-ascii", "windows-1252", "x-cp1252"),
        "windows-1253" to immutableListOf("cp1253", "windows-1253", "x-cp1253"),
        "windows-1254" to immutableListOf("cp1254", "csisolatin5", "iso-8859-9", "iso-ir-148", "iso8859-9", "iso88599", "iso_8859-9", "iso_8859-9:1989", "l5", "latin5", "windows-1254", "x-cp1254"),
        "windows-1255" to immutableListOf("cp1255", "windows-1255", "x-cp1255"),
        "windows-1256" to immutableListOf("cp1256", "windows-1256", "x-cp1256"),
        "windows-1257" to immutableListOf("cp1257", "windows-1257", "x-cp1257"),
        "windows-1258" to immutableListOf("cp1258", "windows-1258", "x-cp1258"),
        "x-mac-cyrillic" to immutableListOf("x-mac-cyrillic", "x-mac-ukrainian"),
        // Legacy multi-byte Chinese (simplified) encodings
        "GBK" to immutableListOf("chinese", "csgb2312", "csiso58gb231280", "gb2312", "gb_2312", "gb_2312-80", "gbk", "iso-ir-58", "x-gbk"),
        "gb18030" to immutableListOf("gb18030"),
        // Legacy multi-byte Chinese (traditional) encodings
        "Big5" to immutableListOf( 	"big5", "big5-hkscs", "cn-big5", "csbig5", "x-x-big5"),
        // Legacy multi-byte Japanese encodings
        "EUC-JP" to immutableListOf("cseucpkdfmtjapanese", "euc-jp", "x-euc-jp"),
        "ISO-2022-JP" to immutableListOf("csiso2022jp", "iso-2022-jp"),
        "Shift_JIS" to immutableListOf("csshiftjis", "ms932", "ms_kanji", "shift-jis", "shift_jis", "sjis", "windows-31j", "x-sjis"),
        // Legacy multi-byte Korean encodings
        "EUC-KR" to immutableListOf("cseuckr", "csksc56011987", "euc-kr", "iso-ir-149", "korean", "ks_c_5601-1987", "ks_c_5601-1989", "ksc5601", "ksc_5601", "windows-949"),
        // Legacy miscellaneous encodings
        "replacement" to immutableListOf("csiso2022kr", "hz-gb-2312", "iso-2022-cn", "iso-2022-cn-ext", "iso-2022-kr", "replacement"),
        "UTF-16BE" to immutableListOf("unicodefffe", "utf-16be"),
        "UTF-16LE" to immutableListOf("csunicode", "iso-10646-ucs-2", "ucs-2", "unicode", "unicodefeff", "utf-16", "utf-16le"),
        "x-user-defined" to immutableListOf("x-user-defined")
    )

    actual val availableCharsets: Map<String, Charset> by lazy {
        JavaScriptEncodingsAndTheirLabels.mapNotNull { (name, _) ->
            forName(name)?.let { name to it }
        }.toImmutableMap()
    }

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