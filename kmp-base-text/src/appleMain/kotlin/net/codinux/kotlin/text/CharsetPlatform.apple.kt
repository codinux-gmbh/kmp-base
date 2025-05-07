@file:OptIn(BetaInteropApi::class)

package net.codinux.kotlin.text

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.UnsafeNumber
import net.codinux.kotlin.lang.ByteArrayBuilder
import net.codinux.kotlin.platform.toByteArray
import net.codinux.kotlin.platform.toNSData
import net.codinux.kotlin.platform.toNSString
import platform.Foundation.*

@OptIn(UnsafeNumber::class)
internal actual object CharsetPlatform {

    private val platformEncodings = listOf(

        // 7- and 8-bit Encodings

        /**
         * Strict 7-bit ASCII encoding within 8-bit chars; ASCII values 0…127 only.
         */
        NSASCIIStringEncoding,

        /**
         * 8-bit ISO Latin 1 encoding.
         */
        NSISOLatin1StringEncoding,

        /**
         * 8-bit ISO Latin 2 encoding.
         */
        NSISOLatin2StringEncoding,

        /**
         * Classic Macintosh Roman encoding.
         */
        NSMacOSRomanStringEncoding,

        /**
         * 8-bit ASCII encoding with NEXTSTEP extensions.
         */
        NSNEXTSTEPStringEncoding,

        /**
         * 7-bit verbose ASCII to represent all Unicode characters.
         */
        NSNonLossyASCIIStringEncoding,

        /**
         * 8-bit Adobe Symbol encoding vector.
         */
        NSSymbolStringEncoding,

        // 7- and 8-bit Japanese Encodings

        /**
         * ISO 2022 Japanese encoding for email.
         */
        NSISO2022JPStringEncoding,

        /**
         * 8-bit EUC encoding for Japanese text.
         */
        NSJapaneseEUCStringEncoding,

        /**
         * 8-bit Shift-JIS encoding for Japanese text.
         */
        NSShiftJISStringEncoding,

        // Unicode Encodings

        /**
         * An 8-bit representation of Unicode characters, suitable for transmission or storage by ASCII-based systems.
         */
        NSUTF8StringEncoding,

        /**
         * NSUTF16StringEncoding encoding with explicit endianness specified.
         */
        NSUTF16BigEndianStringEncoding,

        /**
         * NSUTF16StringEncoding encoding with explicit endianness specified.
         */
        NSUTF16LittleEndianStringEncoding,

        NSUTF16StringEncoding,

        /**
         * The canonical Unicode encoding for string objects.
         */
        NSUnicodeStringEncoding,

        /**
         * NSUTF32StringEncoding encoding with explicit endianness specified.
         */
        NSUTF32BigEndianStringEncoding,

        /**
         * NSUTF32StringEncoding encoding with explicit endianness specified.
         */
        NSUTF32LittleEndianStringEncoding,

        /**
         * 32-bit UTF encoding.
         */
        NSUTF32StringEncoding,

        // Windows Code Page Encodings

        /**
         * Microsoft Windows codepage 1250; equivalent to WinLatin2.
         */
        NSWindowsCP1250StringEncoding,

        /**
         * Microsoft Windows codepage 1251, encoding Cyrillic characters; equivalent to AdobeStandardCyrillic font encoding.
         */
        NSWindowsCP1251StringEncoding,

        /**
         * Microsoft Windows codepage 1252; equivalent to WinLatin1.
         */
        NSWindowsCP1252StringEncoding,

        /**
         * Microsoft Windows codepage 1253, encoding Greek characters.
         */
        NSWindowsCP1253StringEncoding,

        /**
         * Microsoft Windows codepage 1254, encoding Turkish characters.
         */
        NSWindowsCP1254StringEncoding
    )

    // TODO Use the return value of NSString.availableStringEncodings() to get all supported encodings
    actual val availableCharsets: Map<String, Charset> = platformEncodings
        .mapNotNull { mapToCharset(it) }
        .map { it.name to it }
        .toMap()

    actual fun forName(charsetName: String): Charset? = availableCharsets[charsetName]

    private fun mapToCharset(stringEncoding: NSStringEncoding): Charset? {
        val stringEncodingName = NSString.localizedNameOfStringEncoding(stringEncoding)
        val name = extractCharsetName(stringEncodingName)

        return FoundationCharset(name, stringEncoding)
    }

    private fun extractCharsetName(name: String) = when (name) {
        "Western (ISO Latin 1)" -> "ISO-8859-1"
        "Central European (ISO Latin 2)" -> "ISO-8859-2"
        "Japanese (ISO 2022-JP)" -> "ISO-2022-JP"
        "Japanese (EUC)" -> "EUC-JP"
        "Japanese (Windows, DOS)" -> "Shift_JIS"
        "Central European (Windows Latin 2)" -> "windows-1250"
        "Cyrillic (Windows)" -> "windows-1251"
        "Western (Windows Latin 1)" -> "windows-1252"
        "Greek (Windows)" -> "windows-1253"
        "Turkish (Windows Latin 5)" -> "windows-1254"
        "Western (Mac OS Roman)" -> "x-MacRoman"
        "Symbol (Mac OS)" -> "x-MacSymbol"
        else -> {
            // extract charset name from e.g. Western (ASCII), Unicode (UTF-8), ...
            val indexOfOpeningBrace = name.indexOf('(').takeIf { it != -1 }
            val indexOfClosingBrace = name.indexOf(')').takeIf { it != -1 }

            if (indexOfOpeningBrace != null && indexOfClosingBrace != null) {
                name.substring(indexOfOpeningBrace + 1, indexOfClosingBrace)
            } else name
        }
    }

}

@OptIn(UnsafeNumber::class)
class FoundationCharset(name: String, private val stringEncoding: NSStringEncoding) : Charset(name) {

    override fun encode(out: ByteArrayBuilder, src: CharSequence, start: Int, end: Int) {
        try {
            val nsString = src.substring(start, end).toNSString()
            val nsData = nsString.dataUsingEncoding(stringEncoding)

            nsData?.toByteArray()?.let {  bytes ->
                out.append(bytes)
            }
        } catch (e: Exception) {
            println("Could not encode String '$src' with Encoding '$name': $e")
        }
    }

    override fun decode(out: StringBuilder, src: ByteArray, start: Int, end: Int): Int {
        try {
            val nsData = src.sliceArray(start until end).toNSData()
            val nsString = NSString.create(nsData, stringEncoding)

            out.append(nsString?.toString())

            return end - start
        } catch (e: Exception) {
            println("Could not decode ByteArray to String with Encoding '$name': $e")
        }

        return 0
    }

}