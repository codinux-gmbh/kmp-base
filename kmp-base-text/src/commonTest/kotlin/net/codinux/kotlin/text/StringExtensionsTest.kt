package net.codinux.kotlin.text

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class StringExtensionsTest {

    @Test
    fun codePointWithoutSupplementaryCharacter() {
        val result = Char.fromCodePoint(197)

        assertThat(result).isEqualTo(charArrayOf('Å'))
    }

    @Test
    fun codePointWithSupplementaryCharacter() {
        val result = String.fromCodePoint(135361)

        assertThat(result).isEqualTo("𡃁")
    }

}