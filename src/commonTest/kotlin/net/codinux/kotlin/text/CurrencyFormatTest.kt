package net.codinux.kotlin.text

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import net.codinux.kotlin.Platform
import kotlin.test.Test

class CurrencyFormatTest {

    @Test
    fun format_en_US() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = CurrencyFormat.getForLocale(Locale("en", "US"))
        format.shouldNotBeNull()

        val result = format.format(123.45)

        result.shouldBe("$123.45")
    }

    @Test
    fun format_de_DE() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = CurrencyFormat.getForLocale(Locale("de", "DE"))
        format.shouldNotBeNull()

        val result = format.format(123.45)

        result.shouldBe("123,45 €")
    }

    @Test
    fun format_de_CH() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = CurrencyFormat.getForLocale(Locale("de", "CH"))
        format.shouldNotBeNull()

        val result = format.format(123.45)

        result.shouldBe("CHF 123.45")
    }

    @Test
    fun format_en_US_isoCode() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val result = CurrencyFormat.getForLocale(Locale("en", "US"), true)
            ?.format(123.45)

        result
            ?.replace(" ", "") // JS and Apple systems insert a white space, JVM doesn't
            .shouldBe("USD123.45")
    }

    @Test
    fun format_de_DE_isoCode() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val result = CurrencyFormat.getForLocale(Locale("de", "DE"), true)
            ?.format(123.45)

        result.shouldBe("123,45 EUR")
    }

    @Test
    fun format_de_CH_isoCode() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val result = CurrencyFormat.getForLocale(Locale("de", "CH"), true)
            ?.format(123.45)

        result.shouldBe("CHF 123.45")
    }

    @Test
    fun getForLocale_notExistingLocale() {
        val format = CurrencyFormat.getForLocale(Locale("abc", "def"))

        format.shouldBeNull()
    }

}