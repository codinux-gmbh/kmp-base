package net.codinux.kotlin.util

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class URLResolverTest {

    companion object {
        const val BaseUrl = "https://www.codinux.net"
        const val BaseUrlWithPath = "$BaseUrl/path1/path2/path3"
        const val Query = "?name1=value1&name2=value2"
        const val Fragment = "#fragment"
        const val BaseUrlWithPathQueryAndFragment = "$BaseUrlWithPath$Query$Fragment"
    }


    private val underTest = URLResolver()

    @Test
    fun domainRelativeUrl() {
        val domainRelativeUrl = "//codinux.de/start.html"

        val result = underTest.resolveUrl(BaseUrlWithPathQueryAndFragment, domainRelativeUrl)

        result.shouldBe("https:" + domainRelativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = underTest.resolveUrl(BaseUrlWithPath + "/", relativeUrl)

        result.shouldBe(BaseUrl + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashAndRelativeUrlStartsWithLetter() {
        val relativeUrl = "path4"

        val result = underTest.resolveUrl(BaseUrlWithPath + "/", relativeUrl)

        result.shouldBe(BaseUrlWithPath + "/" + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithLetterAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = underTest.resolveUrl(BaseUrlWithPath, relativeUrl)

        result.shouldBe(BaseUrl + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashQueryAndFragmentAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = underTest.resolveUrl("$BaseUrlWithPath/$Query$Fragment", relativeUrl)

        result.shouldBe(BaseUrl + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashQueryAndFragmentAndRelativeUrlStartsWithLetter() {
        val relativeUrl = "path4"

        val result = underTest.resolveUrl("$BaseUrlWithPath/$Query$Fragment", relativeUrl)

        result.shouldBe(BaseUrlWithPath + "/" + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithLetterQueryAndFragmentAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = underTest.resolveUrl(BaseUrlWithPathQueryAndFragment, relativeUrl)

        result.shouldBe(BaseUrl + relativeUrl)
    }

}