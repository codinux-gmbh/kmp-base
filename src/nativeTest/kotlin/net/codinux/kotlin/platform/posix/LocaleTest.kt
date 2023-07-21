package net.codinux.kotlin.platform.posix

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlin.test.Test

class LocaleTest {

    @Test
    fun `parseToLocale - language only`() {
        val language = "hu"

        val result = Locale.parseToLocale(language)

        result.language.shouldBe(language)
        result.country.shouldBeEmpty()
        result.variant.shouldBeNull()
        result.script.shouldBeNull()
    }

    @Test
    fun `parseToLocale - language and country`() {
        val language = "ja"
        val country = "JP"

        val result = Locale.parseToLocale(language + "_" + country)

        result.language.shouldBe(language)
        result.country.shouldBe(country)
        result.variant.shouldBeNull()
        result.script.shouldBeNull()
    }

    @Test
    fun `parseToLocale - language country and encoding`() {
        val language = "ja"
        val country = "JP"
        val encoding = "UTF-8"

        val result = Locale.parseToLocale(language + "_" + country + "." + encoding)

        result.language.shouldBe(language)
        result.country.shouldBe(country)
        result.variant.shouldBeNull()
        result.script.shouldBeNull()
    }

    @Test
    fun `parseToLocale - language country and variant`() {
        val language = "no"
        val country = "no"
        val variant = "nynorsk"

        val result = Locale.parseToLocale(language + "_" + country + "@" + variant)

        result.language.shouldBe(language)
        result.country.shouldBe(country)
        result.variant.shouldBe(variant)
        result.script.shouldBeNull()
    }

    @Test
    fun `parseToLocale - language country encoding and variant`() {
        val language = "no"
        val country = "no"
        val encoding = "UTF-8"
        val variant = "nynorsk"

        val result = Locale.parseToLocale(language + "_" + country + "." + encoding + "@" + variant)

        result.language.shouldBe(language)
        result.country.shouldBe(country)
        result.variant.shouldBe(variant)
        result.script.shouldBeNull()
    }

}