package net.codinux.kotlin.text

import net.codinux.kotlin.test.Assertions.assertElementsEqual
import kotlin.test.Test

class CharExtensionsTest {

    @Test
    fun codePointWithoutSupplementaryCharacter() {
        val result = Char.fromCodePoint("Å".getCodePointAt(0))

        assertElementsEqual(result, charArrayOf('Å'))
    }

    @Test
    fun codePointWithSupplementaryCharacter() {
        val result = Char.fromCodePoint("𡃁".getCodePointAt(0))

        assertElementsEqual(result, charArrayOf('\uD844', '\uDCC1'))
    }

}