package net.codinux.kotlin.text

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

@OptIn(ExperimentalStdlibApi::class)
val String.unhex get() = this.hexToByteArray()

}