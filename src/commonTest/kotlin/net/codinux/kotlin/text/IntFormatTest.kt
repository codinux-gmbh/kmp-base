package net.codinux.kotlin.text

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import net.codinux.kotlin.Platform
import kotlin.test.Test

class IntFormatTest {

    @Test
    fun format_en_US() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = IntFormat.getForLocale(Locale("en", "US"))
        format.shouldNotBeNull()

        val result = format.format(123)

        result.shouldBe("123")
    }

    @Test
    fun format_de_DE() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = IntFormat.getForLocale(Locale("de", "DE"))
        format.shouldNotBeNull()

        val result = format.format(123)

        result.shouldBe("123")
    }

    @Test
    fun format_de_CH() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = IntFormat.getForLocale(Locale("de", "CH"))
        format.shouldNotBeNull()

        val result = format.format(123)

        result.shouldBe("123")
    }

    @Test
    fun getForLocale_notExistingLocale() {
        val format = IntFormat.getForLocale(Locale("abc", "def"))

        format.shouldBeNull()
    }

}