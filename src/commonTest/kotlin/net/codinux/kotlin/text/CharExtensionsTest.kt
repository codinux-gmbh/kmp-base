package net.codinux.kotlin.text

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CharExtensionsTest {

    @Test
    fun codePointWithoutSupplementaryCharacter() {
        val result = Char.fromCodePoint("Å".getCodePointAt(0))

        result.shouldBe(charArrayOf('Å'))
    }

    @Test
    fun codePointWithSupplementaryCharacter() {
        val result = Char.fromCodePoint("𡃁".getCodePointAt(0))

        result.shouldBe(charArrayOf('\uD844', '\uDCC1'))
    }

}