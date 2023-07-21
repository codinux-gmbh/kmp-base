package net.codinux.kotlin.util

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.ints.shouldBeBetween
import kotlin.test.Test

class LocaleTest {

    @Test
    fun getSystemDefaultLocale() {
        val result = Locale.getDefault()

        // the language either has 2-3 or 5-8 characters
        val range1 = 2 .. 3
        val range2 = 5 .. 8
        val languageHasCorrectLength = result.language.length.let { length ->
            range1.contains(length) || range2.contains(length)
        }
        languageHasCorrectLength.shouldBeTrue()

        result.country.length.shouldBeBetween(2, 3)
    }

}