package net.codinux.kotlin.text

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import net.codinux.kotlin.Platform
import kotlin.test.Test

class DecimalFormatTest {

    @Test
    fun format_en_US() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = DecimalFormat.getForLocale(Locale("en", "US"))
        format.shouldNotBeNull()

        val result = format.format(123.45)

        result.shouldBe("123.45")
    }

    @Test
    fun format_de_DE() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = DecimalFormat.getForLocale(Locale("de", "DE"))
        format.shouldNotBeNull()

        val result = format.format(123.45)

        result.shouldBe("123,45")
    }

    @Test
    fun format_de_CH() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = DecimalFormat.getForLocale(Locale("de", "CH"))
        format.shouldNotBeNull()

        val result = format.format(123.45)

        result.shouldBe("123.45")
    }

    @Test
    fun getForLocale_notExistingLocale() {
        val format = DecimalFormat.getForLocale(Locale("abc", "def"))

        format.shouldBeNull()
    }


    @Test
    fun minimumIntegerDigits() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = DecimalFormat.getForLocale(Locale("en", "US"), NumberFormat(minimumIntegerDigits = 7))!!

        val result = format.format(1234.0)

        result.shouldBe("0,001,234")
    }

    @Test
    fun maximumIntegerDigits() {
        if (Platform.type.isLinuxOrMingw || Platform.type.isJavaScript) {
            return
        }

        val format = DecimalFormat.getForLocale(Locale("en", "US"), NumberFormat(maximumIntegerDigits = 2))!!

        val result = format.format(1234.0)

        result.shouldBe("34")
    }

    @Test
    fun minimumFractionDigits() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = DecimalFormat.getForLocale(Locale("en", "US"), NumberFormat(minimumFractionDigits = 4))!!

        val result = format.format(12.34)

        result.shouldBe("12.3400")
    }

    @Test
    fun maximumFractionDigits() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = DecimalFormat.getForLocale(Locale("en", "US"), NumberFormat(maximumFractionDigits = 2))!!

        val result = format.format(1.234567)

        result.shouldBe("1.23")
    }

    @Test
    fun useGrouping_True() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = DecimalFormat.getForLocale(Locale("en", "US"), NumberFormat(useGrouping = true))!!

        val result = format.format(1234567.0)

        result.shouldBe("1,234,567")
    }

    @Test
    fun useGrouping_False() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = DecimalFormat.getForLocale(Locale("en", "US"), NumberFormat(useGrouping = false))!!

        val result = format.format(1234567.0)

        result.shouldBe("1234567")
    }

}