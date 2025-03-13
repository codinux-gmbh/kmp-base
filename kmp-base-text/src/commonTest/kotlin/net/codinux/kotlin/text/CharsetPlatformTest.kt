package net.codinux.kotlin.text

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThanOrEqualTo
import assertk.assertions.isNotNull
import net.codinux.kotlin.Platform
import net.codinux.kotlin.PlatformType
import net.codinux.kotlin.collections.containsNot
import net.codinux.kotlin.isAppleOS
import net.codinux.kotlin.isLinuxOrMingw
import kotlin.test.Test
import kotlin.test.assertEquals

class CharsetPlatformTest {

    companion object {
        private val CharsetsNotSupportingAscii = listOf(
            "JIS_X0212-1990", "x-COMPOUND_TEXT", "x-IBM300", "x-IBM834", "x-JIS0208", "Mac OS", "x-MacDingbat", "x-MacSymbol"
        )
    }


    @Test
    fun availableCharsets() {
        if (Platform.isLinuxOrMingw == false) {
            val availableCharsets = CharsetPlatform.availableCharsets

            if (Platform.isAppleOS && Platform.type != PlatformType.macOS) {
                assertThat(availableCharsets.size).isGreaterThanOrEqualTo(9) // iOS, watchOS and tvOS have only 9 charsets
            } else {
                assertThat(availableCharsets.size).isGreaterThanOrEqualTo(22)
            }
        }
    }

    @Test
    fun forName() {
        val availableCharsets = CharsetPlatform.availableCharsets

        availableCharsets.forEach { (name, _) ->
            assertThat(CharsetPlatform.forName(name)).isNotNull()
        }
    }

    @Test
    fun encodeAndDecode_ASCII() {
        val availableCharsets = CharsetPlatform.availableCharsets
        println("${Platform.type} has ${availableCharsets.size} charsets")

        val testString = StringTestData.Ascii

        availableCharsets.forEach { (_, charset) ->
            if (charset.canEncode) {
                if (CharsetsNotSupportingAscii.containsNot(charset.name)) {
                    val encoded = testString.toByteArray(charset)
                    val decoded = encoded.toString(charset)

                    assertEquals(testString, decoded, "Charset '${charset.name}' should be able to encode and decode ASCII characters")
                }
            } else { // Charsets ISO-2022-CN and x-JISAutoDetect cannot encode
                println("Charset ${charset.name} cannot encode")
            }
        }
    }

    @Test
    fun encodeAndDecode_Unicode_Latin() {
        val availableCharsets = CharsetPlatform.availableCharsets

        val testString = StringTestData.Latin

        availableCharsets.forEach { (_, charset) ->
            if (charset.canEncode && charset.isUnicodeCharset) {
                val encoded = testString.toByteArray(charset)
                val decoded = encoded.toString(charset)

                assertEquals(testString, decoded)
            }
        }
    }


    @Test
    fun forName_IsoLatin2() {
        assertCharsetForName("ISO-8859-2")
    }

    @Test
    fun forName_KOI8_R() {
        assertCharsetForName("KOI8-R")
    }

    @Test
    fun forName_EUC_JP() {
        assertCharsetForName("EUC-JP")
    }

    @Test
    fun forName_Shift_JIS() {
        assertCharsetForName("Shift_JIS")
    }

    @Test
    fun forName_GB2312() {
        assertCharsetForName("GB2312")
    }

    @Test
    fun forName_GBK() {
        assertCharsetForName("GBK")
    }

    @Test
    fun forName_Big5() {
        assertCharsetForName("Big5")
    }

    private fun assertCharsetForName(charsetName: String) {
        if (Platform.isLinuxOrMingw) {
            return
        }

        val result = Charset.forName(charsetName)

        assertThat(result).isNotNull()
        assertThat(result!!.name).isEqualTo(charsetName)
    }

}