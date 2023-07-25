package net.codinux.kotlin.util

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class URLResolverTest {

    companion object {
        private const val BaseUrlWithoutQueryAndFragment = "https://www.codinux.net/path1/path2/path3"
        private const val Query = "?name1=value1&name2=value2"
        private const val Fragment = "#fragment"
        private const val BaseUrl = "$BaseUrlWithoutQueryAndFragment$Query$Fragment"
    }


    private val underTest = URLResolver()

    @Test
    fun domainRelativeUrl() {
        val domainRelativeUrl = "//codinux.de/start.html"

        val result = underTest.resolveUrl(BaseUrl, domainRelativeUrl)

        result.shouldBe("https:" + domainRelativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = underTest.resolveUrl(BaseUrlWithoutQueryAndFragment + "/", relativeUrl)

        result.shouldBe(BaseUrlWithoutQueryAndFragment + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashAndRelativeUrlStartsWithLetter() {
        val relativeUrl = "path4"

        val result = underTest.resolveUrl(BaseUrlWithoutQueryAndFragment + "/", relativeUrl)

        result.shouldBe(BaseUrlWithoutQueryAndFragment + "/" + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithLetterAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = underTest.resolveUrl(BaseUrlWithoutQueryAndFragment, relativeUrl)

        result.shouldBe(BaseUrlWithoutQueryAndFragment + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashQueryAndFragmentAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = underTest.resolveUrl("$BaseUrlWithoutQueryAndFragment/$Query$Fragment", relativeUrl)

        result.shouldBe(BaseUrlWithoutQueryAndFragment + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashQueryAndFragmentAndRelativeUrlStartsWithLetter() {
        val relativeUrl = "path4"

        val result = underTest.resolveUrl("$BaseUrlWithoutQueryAndFragment/$Query$Fragment", relativeUrl)

        result.shouldBe(BaseUrlWithoutQueryAndFragment + "/" + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithLetterQueryAndFragmentAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = underTest.resolveUrl(BaseUrl, relativeUrl)

        result.shouldBe(BaseUrlWithoutQueryAndFragment + relativeUrl)
    }

}