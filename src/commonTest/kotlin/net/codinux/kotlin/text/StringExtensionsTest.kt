package net.codinux.kotlin.text

import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlin.test.assertEquals

class StringExtensionsTest {

    @Test
    fun codePointWithoutSupplementaryCharacter() {
        val result = Char.fromCodePoint(197)

        result.shouldBe(charArrayOf('Å'))
    }

    @Test
    fun codePointWithSupplementaryCharacter() {
        val result = String.fromCodePoint(135361)

        assertEquals("𡃁", result)
    }

}