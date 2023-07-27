package net.codinux.kotlin.text

import net.codinux.kotlin.test.Assertions
import kotlin.test.Test
import kotlin.test.assertEquals

class StringExtensionsTest {

    @Test
    fun codePointWithoutSupplementaryCharacter() {
        val result = Char.fromCodePoint(197)

        Assertions.assertElementsEqual(result, charArrayOf('Å'))
    }

    @Test
    fun codePointWithSupplementaryCharacter() {
        val result = String.fromCodePoint(135361)

        assertEquals("𡃁", result)
    }

}