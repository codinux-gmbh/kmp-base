package net.codinux.kotlin.text

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class CharExtensionsTest {

    @Test
    fun codePointWithoutSupplementaryCharacter() {
        val result = Char.fromCodePoint("Å".getCodePointAt(0))

        assertThat(result).isEqualTo(charArrayOf('Å'))
    }

    @Test
    fun codePointWithSupplementaryCharacter() {
        val result = Char.fromCodePoint("𡃁".getCodePointAt(0))

        assertThat(result).isEqualTo(charArrayOf('\uD844', '\uDCC1'))
    }

}