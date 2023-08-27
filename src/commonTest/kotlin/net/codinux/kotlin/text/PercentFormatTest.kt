package net.codinux.kotlin.text

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import net.codinux.kotlin.Platform
import kotlin.test.Test

class PercentFormatTest {

    @Test
    fun format_en_US() {
        if (Platform.type.isLinuxOrMingw) {
            return
        }

        val format = PercentFormat.getForLocale(Locale("en", "US"))
        format.shouldNotBeNull()

        val result = format.format(0.1234)

        result.shouldBe("12%")
    }

    // for other Locales the platforms handle the percent style differently:
    // - for de-DE JVM formats it without white space, JS and Apple systems with white space
    // - for de-CH JVM formats it with white space, JS and Apple system without white space

    @Test
    fun getForLocale_notExistingLocale() {
        val format = PercentFormat.getForLocale(Locale("abc", "def"))

        format.shouldBeNull()
    }

}