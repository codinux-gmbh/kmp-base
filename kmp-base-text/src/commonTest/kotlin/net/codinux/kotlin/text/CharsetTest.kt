package net.codinux.kotlin.text

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThanOrEqualTo
import assertk.assertions.isNull
import kotlin.test.Test
import kotlin.test.assertEquals

// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/korio/src/commonTest/kotlin/korlibs/io/lang/CharsetTest.kt

class CharsetTest {

    @Test
    fun testSurrogatePairs() {
        val text = "{Test\uD83D\uDE00}"
        assertEquals(
            listOf(123, 84, 101, 115, 116, -16, -97, -104, -128, 125),
            text.toByteArray(Charsets.UTF8).map { it.toInt() }
        )
    }

    @Test
    fun testSurrogatePairsTwo() {
        val text = "{Test\uD83D\uDE00}"
        assertEquals(
            text,
            text.toByteArray(Charsets.UTF8).toString(Charsets.UTF8).toByteArray(Charsets.UTF8).toString(Charsets.UTF8)
        )
    }

    @Test
    fun testDecode() {
        val text = byteArrayOf(-87, 32, 50, 48, 48, 57, 32, 45, 32, 50, 48, 49)
        assertEquals(
            "\uFFFD 2009 - 201",
            text.toString(Charsets.UTF8)
        )
    }

    @Test
    fun testSample() {
        val text = (0 until 255).map { it.toChar() }.joinToString("")
        assertEquals(
            text,
            text.toByteArray(Charsets.UTF8).toString(Charsets.UTF8)
        )
    }

    @Test
    fun testUTF16() {
        assertEquals("emoji", "0065006d006f006a0069".unhex.toString(Charsets.UTF16_BE))
        assertEquals("emoji", "65006d006f006a006900".unhex.toString(Charsets.UTF16_LE))
    }

    @Test
    fun testCharsetForName() {
        assertEquals(
            """
                US-ASCII
                US-ASCII
                UTF-8
                UTF-16-LE
                UTF-16-LE
                UTF-16-BE
                ISO-8859-1
                ISO-8859-1
            """.trimIndent(),
            listOf("ASCII", "US-ASCII", "UTF-8", "UTF-16", "UTF-16-LE", "UTF-16-BE", "LATIN-1", "ISO-8859-1")
                .joinToString("\n") { Charset.forName(it)!!.name }
        )
    }

    @Test
    fun testCharsetForName_UnknownCharset() {
        assertThat(Charset.forName("MY-UNKNOWN-CHARSET")).isNull()
    }

    @Test
    fun testPartialDecode() {
        val charset = Charsets.UTF8
        val text = "hello你好"
        val bytes = text.toByteArray(charset)
        val out = StringBuilder()
        val outIncomplete = StringBuilder()
        val read = charset.decode(out, bytes, 1, bytes.size)
        val readIncomplete = charset.decode(outIncomplete, bytes, 1, bytes.size - 1)

        assertEquals(
            """
                'ello你好' - 10
                'ello你' - 7
            """.trimIndent(),
            """
                '$out' - $read
                '$outIncomplete' - $readIncomplete
            """.trimIndent()
        )
    }

    @Test
    fun availableCharsets() {
        val availableCharsets = Charset.availableCharsets

        assertThat(availableCharsets.size).isGreaterThanOrEqualTo(Charsets.StandardCharsets.size)

        // assert availableCharsets contains all standard Charsets
        Charsets.StandardCharsets.forEach { (name, charset) ->
            assertThat(availableCharsets[name]).isEqualTo(charset)
        }
    }


@OptIn(ExperimentalStdlibApi::class)
val String.unhex get() = this.hexToByteArray()

}