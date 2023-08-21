package net.codinux.kotlin.text

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import net.codinux.kotlin.Platform
import kotlin.test.Test

class CurrencyFormatTest {

    @Test
    fun format_en_US() {
        if (Platform.type.isNative) {
            return
        }

        val format = CurrencyFormat.getForLocale(Locale("en", "US"))
        format.shouldNotBeNull()

        val result = format.format(123.45)

        result.shouldBe("$123.45")
    }

    @Test
    fun format_de_DE() {
        if (Platform.type.isNative) {
            return
        }

        val format = CurrencyFormat.getForLocale(Locale("de", "DE"))
        format.shouldNotBeNull()

        val result = format.format(123.45)

        result.shouldBe("123,45 €")
    }

    @Test
    fun format_de_CH() {
        if (Platform.type.isNative) {
            return
        }

        val format = CurrencyFormat.getForLocale(Locale("de", "CH"))
        format.shouldNotBeNull()

        val result = format.format(123.45)

        result.shouldBe("CHF 123.45")
    }

}