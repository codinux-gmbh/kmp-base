package net.codinux.kotlin.text

import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.nulls.shouldNotBeNull
import net.codinux.collections.immutableListOf
import net.codinux.kotlin.Platform
import net.codinux.kotlin.collections.containsNot
import kotlin.test.Test
import kotlin.test.assertEquals

class CharsetPlatformTest {

    companion object {
        private val CharsetsNotSupportingAscii = immutableListOf(
            "JIS_X0212-1990", "x-COMPOUND_TEXT", "x-IBM300", "x-IBM834", "x-JIS0208", "x-MacDingbat", "x-MacSymbol"
        )

        private val icu4jCharsets = immutableListOf(
            "Adobe-Standard-Encoding",
            "Big5",
            "Big5-HKSCS",
            "BOCU-1",
            "CESU-8",
            "cp1363",
            "cp851",
            "EUC-JP",
            "EUC-KR",
            "GB18030",
            "GB2312",
            "GB_2312-80",
            "GBK",
            "hp-roman8",
            "HZ-GB-2312",
            "IBM-Thai",
            "IBM00858",
            "IBM01140",
            "IBM01141",
            "IBM01142",
            "IBM01143",
            "IBM01144",
            "IBM01145",
            "IBM01146",
            "IBM01147",
            "IBM01148",
            "IBM01149",
            "IBM037",
            "IBM1026",
            "IBM1047",
            "IBM273",
            "IBM277",
            "IBM278",
            "IBM280",
            "IBM284",
            "IBM285",
            "IBM290",
            "IBM297",
            "IBM420",
            "IBM424",
            "IBM437",
            "IBM500",
            "IBM775",
            "IBM850",
            "IBM852",
            "IBM855",
            "IBM857",
            "IBM860",
            "IBM861",
            "IBM862",
            "IBM863",
            "IBM864",
            "IBM865",
            "IBM866",
            "IBM868",
            "IBM869",
            "IBM870",
            "IBM871",
            "IBM918",
            "ISO-2022-CN",
            "ISO-2022-CN-EXT",
            "ISO-2022-JP",
            "ISO-2022-JP-1",
            "ISO-2022-JP-2",
            "ISO-2022-KR",
            "ISO-8859-1",
            "ISO-8859-10",
            "ISO-8859-13",
            "ISO-8859-14",
            "ISO-8859-15",
            "ISO-8859-16",
            "ISO-8859-2",
            "ISO-8859-3",
            "ISO-8859-4",
            "ISO-8859-5",
            "ISO-8859-6",
            "ISO-8859-7",
            "ISO-8859-8",
            "ISO-8859-9",
            "JIS_X0201",
            "JIS_X0212-1990",
            "KOI8-R",
            "KOI8-U",
            "KSC_5601",
            "macintosh",
            "SCSU",
            "Shift_JIS",
            "TIS-620",
            "US-ASCII",
            "UTF-16",
            "UTF-16BE",
            "UTF-16LE",
            "UTF-32",
            "UTF-32BE",
            "UTF-32LE",
            "UTF-7",
            "UTF-8",
            "windows-1250",
            "windows-1251",
            "windows-1252",
            "windows-1253",
            "windows-1254",
            "windows-1255",
            "windows-1256",
            "windows-1257",
            "windows-1258",
            "windows-31j",
            "x-Big5-HKSCS-2001",
            "x-Big5-Solaris",
            "x-compound-text",
            "x-ebcdic-xml-us",
            "x-euc-jp-linux",
            "x-EUC-TW",
            "x-euc-tw-2014",
            "x-eucJP-Open",
            "x-gsm-03.38-2009",
            "x-ibm-1047-s390",
            "x-ibm-1125_P100-1997",
            "x-ibm-1129_P100-1997",
            "x-ibm-1130_P100-1997",
            "x-ibm-1131_P100-1997",
            "x-ibm-1132_P100-1998",
            "x-ibm-1133_P100-1997",
            "x-ibm-1137_P100-1999",
            "x-ibm-1140-s390",
            "x-ibm-1141-s390",
            "x-ibm-1142-s390",
            "x-ibm-1143-s390",
            "x-ibm-1144-s390",
            "x-ibm-1145-s390",
            "x-ibm-1146-s390",
            "x-ibm-1147-s390",
            "x-ibm-1148-s390",
            "x-ibm-1149-s390",
            "x-ibm-1153-s390",
            "x-ibm-1154_P100-1999",
            "x-ibm-1155_P100-1999",
            "x-ibm-1156_P100-1999",
            "x-ibm-1157_P100-1999",
            "x-ibm-1158_P100-1999",
            "x-ibm-1160_P100-1999",
            "x-ibm-1162_P100-1999",
            "x-ibm-1164_P100-1999",
            "x-ibm-1250_P100-1995",
            "x-ibm-1251_P100-1995",
            "x-ibm-1252_P100-2000",
            "x-ibm-1253_P100-1995",
            "x-ibm-1254_P100-1995",
            "x-ibm-1255_P100-1995",
            "x-ibm-1256_P110-1997",
            "x-ibm-1257_P100-1995",
            "x-ibm-1258_P100-1997",
            "x-ibm-12712-s390",
            "x-ibm-12712_P100-1998",
            "x-ibm-1373_P100-2002",
            "x-ibm-1386_P100-2001",
            "x-ibm-16684_P110-2003",
            "x-ibm-16804-s390",
            "x-ibm-16804_X110-1999",
            "x-ibm-25546",
            "x-ibm-33722_P12A_P12A-2009_U2",
            "x-ibm-37-s390",
            "x-ibm-4517_P100-2005",
            "x-ibm-4899_P100-1998",
            "x-ibm-4909_P100-1999",
            "x-ibm-4971_P100-1999",
            "x-ibm-5123_P100-1999",
            "x-ibm-5351_P100-1998",
            "x-ibm-5352_P100-1998",
            "x-ibm-5353_P100-1998",
            "x-ibm-803_P100-1999",
            "x-ibm-813_P100-1995",
            "x-ibm-8482_P100-1999",
            "x-ibm-901_P100-1999",
            "x-ibm-902_P100-1999",
            "x-ibm-9067_X100-2005",
            "x-ibm-916_P100-1995",
            "x-IBM1006",
            "x-IBM1025",
            "x-IBM1046",
            "x-IBM1097",
            "x-IBM1098",
            "x-IBM1112",
            "x-IBM1122",
            "x-IBM1123",
            "x-IBM1124",
            "x-IBM1129",
            "x-IBM1153",
            "x-IBM1166",
            "x-IBM1363",
            "x-IBM1364",
            "x-IBM1371",
            "x-IBM1381",
            "x-IBM1383",
            "x-IBM1388",
            "x-IBM1390",
            "x-IBM1399",
            "x-IBM29626C",
            "x-IBM300",
            "x-IBM33722",
            "x-IBM720",
            "x-IBM737",
            "x-IBM833",
            "x-IBM834",
            "x-IBM856",
            "x-IBM867",
            "x-IBM874",
            "x-IBM875",
            "x-IBM921",
            "x-IBM922",
            "x-IBM930",
            "x-IBM933",
            "x-IBM935",
            "x-IBM937",
            "x-IBM939",
            "x-IBM942",
            "x-IBM942C",
            "x-IBM943",
            "x-IBM943C",
            "x-IBM948",
            "x-IBM949",
            "x-IBM949C",
            "x-IBM950",
            "x-IBM954",
            "x-IBM964",
            "x-IBM970",
            "x-IBM971",
            "x-IMAP-mailbox-name",
            "x-iscii-be",
            "x-iscii-gu",
            "x-iscii-ka",
            "x-iscii-ma",
            "x-iscii-or",
            "x-iscii-pa",
            "x-iscii-ta",
            "x-iscii-te",
            "x-ISCII91",
            "x-ISO-2022-CN-CNS",
            "x-ISO-2022-CN-GB",
            "x-iso-8859-11",
            "x-JIS0208",
            "x-JIS7",
            "x-JIS8",
            "x-JISAutoDetect",
            "x-Johab",
            "x-LMBCS-1",
            "x-mac-centraleurroman",
            "x-mac-cyrillic",
            "x-mac-greek",
            "x-mac-turkish",
            "x-MacArabic",
            "x-MacCentralEurope",
            "x-MacCroatian",
            "x-MacCyrillic",
            "x-MacDingbat",
            "x-MacGreek",
            "x-MacHebrew",
            "x-MacIceland",
            "x-MacRoman",
            "x-MacRomania",
            "x-MacSymbol",
            "x-MacThai",
            "x-MacTurkish",
            "x-MacUkraine",
            "x-MS932_0213",
            "x-MS950-HKSCS",
            "x-MS950-HKSCS-XP",
            "x-mswin-936",
            "x-PCK",
            "x-SJIS_0213",
            "x-UnicodeBig",
            "x-UTF-16LE-BOM",
            "X-UTF-32BE-BOM",
            "X-UTF-32LE-BOM",
            "x-UTF16_OppositeEndian",
            "x-UTF16_PlatformEndian",
            "x-UTF32_OppositeEndian",
            "x-UTF32_PlatformEndian",
            "x-windows-50220",
            "x-windows-50221",
            "x-windows-874",
            "x-windows-949",
            "x-windows-950",
            "x-windows-iso2022jp"
        )
    }


    @Test
    fun availableCharsets() {
        if (Platform.type.isJvmOrAndroid || Platform.type.isJavaScript) {
            val availableCharsets = CharsetPlatform.availableCharsets

            availableCharsets.entries.shouldHaveAtLeastSize(20)
        }
    }

    @Test
    fun forName() {
        val availableCharsets = CharsetPlatform.availableCharsets

        availableCharsets.forEach { (name, _) ->
            CharsetPlatform.forName(name).shouldNotBeNull()
        }
    }

    @Test
    fun encodeAndDecode_ASCII() {
        val availableCharsets = CharsetPlatform.availableCharsets
        println("${Platform.type} has ${availableCharsets.size} charsets")

        val testString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"

        availableCharsets.forEach { (_, charset) ->
            if (charset.canEncode) {
                if (CharsetsNotSupportingAscii.containsNot(charset.name)) {
                    val encoded = testString.toByteArray(charset)
                    val decoded = encoded.toString(charset)

                    assertEquals(testString, decoded)
                }
            } else { // Charsets ISO-2022-CN and x-JISAutoDetect cannot encode
                println("Charset ${charset.name} cannot encode")
            }
        }
    }

    @Test
    fun encodeAndDecode_Unicode_Latin() {
        val availableCharsets = CharsetPlatform.availableCharsets

        val testString =
            // from https://en.wikipedia.org/wiki/ISO/IEC_8859-15#Codepage_layout
            "¡ \t¢ \t£ \t€ \t¥ \tŠ \t§ \tš " +
                "Ž\n \tµ \t¶ \t· \tž\n \t¹ \tº \t» \tŒ \tœ \tŸ \t¿ " +
                "À \tÁ \tÂ \tÃ \tÄ \tÅ \tÆ \tÇ \tÈ \tÉ \tÊ \tË \tÌ \tÍ \tÎ \tÏ " +
                "Ð \tÑ \tÒ \tÓ \tÔ \tÕ \tÖ \t× \tØ \tÙ \tÚ \tÛ \tÜ \tÝ \tÞ \tß " +
                "à \tá \tâ \tã \tä \tå \tæ \tç \tè \té \tê \të \tì \tí \tî \tï " +
                "ð \tñ \tò \tó \tô \tõ \tö \t÷ \tø \tù \tú \tû \tü \tý \tþ \tÿ Ǿ ǿ " +
                // from https://en.wikipedia.org/wiki/ISO-8859-1#Languages_with_incomplete_coverage
                "Œ, œ Ǿ, ǿ Ĳ, ĳ Ŀ, ŀ ẞ Š, š, Ž, ž Ő, ő, Ű, ű " + // French, Danish, Dutch, Catalan, German, Estonian, Finnish, Hungarian
                "Ḃ, ḃ, Ċ, ċ, Ḋ, ḋ, Ḟ, ḟ, Ġ, ġ, Ṁ, ṁ, Ṗ, ṗ, Ṡ, ṡ, Ṫ, ṫ " + // Irish
                "Ẁ, ẁ, Ẃ, ẃ, Ŵ, ŵ, Ẅ, ẅ, Ỳ, ỳ, Ŷ, ŷ, Ÿ" // Welsh

        availableCharsets.forEach { (_, charset) ->
            if (charset.canEncode && charset.isUnicodeCharset) {
                val encoded = testString.toByteArray(charset)
                val decoded = encoded.toString(charset)

                assertEquals(testString, decoded)
            }
        }
    }
}