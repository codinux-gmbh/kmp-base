package net.codinux.kotlin.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class URLTest {

    @Test
    fun schemeIsSet() {
        val result = URL("https://www.codinux.net")

        result.scheme.shouldBe("https")
    }

    @Test
    fun schemeIsNotSet() {
        shouldThrow<IllegalArgumentException> {
            URL("www.codinux.net")
        }
    }

    @Test
    fun hostIsSet() {
        val result = URL("https://www.codinux.net")

        result.host.shouldBe("www.codinux.net")
    }

    @Test
    fun hostIsNotSet() {
        val result = URL("https:/index.html")

        result.host.shouldBeNull()
    }

    @Test
    fun portIsSet() {
        val result = URL("https://www.codinux.net:8080")

        result.port.shouldBe(8080)
    }

    @Test
    fun portIsSetToProtocolDefaultPort() {
        val result = URL("https://www.codinux.net:443")

        result.port.shouldBe(443)
    }

    @Test
    fun portIsNotSet() {
        val result = URL("https://www.codinux.net")

        result.port.shouldBeNull()
    }

    @Test
    fun pathIsSet() {
        val result = URL("https://www.codinux.net/path1/path2/")

        result.path.shouldBe("path1/path2/")
    }

    @Test
    fun pathIsNotSet() {
        val result = URL("https://www.codinux.net/")

        result.path.shouldBeNull()
    }

    @Test
    fun queryIsSet() {
        val result = URL("https://www.codinux.net/path1/path2/?name1=value2&name2=value2")

        result.query.shouldBe("name1=value2&name2=value2")
    }

    @Test
    fun queryIsNotSet() {
        val result = URL("https://www.codinux.net/path1/path2/")

        result.query.shouldBeNull()
    }

    @Test
    fun fragmentIsSet() {
        val result = URL("https://www.codinux.net/path1/path2/#fragment")

        result.fragment.shouldBe("fragment")
    }

    @Test
    fun fragmentIsNotSet() {
        val result = URL("https://www.codinux.net/path1/path2/")

        result.fragment.shouldBeNull()
    }

    @Test
    fun queryAndFragment() {
        val result = URL("https://www.codinux.net/path1/path2/?name1=value2&name2=value2#fragment")

        result.path.shouldBe("path1/path2/")
        result.query.shouldBe("name1=value2&name2=value2")
        result.fragment.shouldBe("fragment")
    }


    @Test
    fun domainRelativeUrl() {
        val domainRelativeUrl = "//codinux.de/start.html"

        val result = URL(URLResolverTest.BaseUrlWithPathQueryAndFragment, domainRelativeUrl)

        result.toString().shouldBe("https:" + domainRelativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = URL(URLResolverTest.BaseUrlWithPath + "/", relativeUrl)

        result.toString().shouldBe(URLResolverTest.BaseUrl + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashAndRelativeUrlStartsWithLetter() {
        val relativeUrl = "path4"

        val result = URL(URLResolverTest.BaseUrlWithPath + "/", relativeUrl)

        result.toString().shouldBe(URLResolverTest.BaseUrlWithPath + "/" + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithLetterAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = URL(URLResolverTest.BaseUrlWithPath, relativeUrl)

        result.toString().shouldBe(URLResolverTest.BaseUrl + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashQueryAndFragmentAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = URL("${URLResolverTest.BaseUrlWithPath}/${URLResolverTest.Query}${URLResolverTest.Fragment}", relativeUrl)

        result.toString().shouldBe(URLResolverTest.BaseUrl + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithSlashQueryAndFragmentAndRelativeUrlStartsWithLetter() {
        val relativeUrl = "path4"

        val result = URL("${URLResolverTest.BaseUrlWithPath}/${URLResolverTest.Query}${URLResolverTest.Fragment}", relativeUrl)

        result.toString().shouldBe(URLResolverTest.BaseUrlWithPath + "/" + relativeUrl)
    }

    @Test
    fun baseUrlEndsWithLetterQueryAndFragmentAndRelativeUrlStartsWithSlash() {
        val relativeUrl = "/path4"

        val result = URL(URLResolverTest.BaseUrlWithPathQueryAndFragment, relativeUrl)

        result.toString().shouldBe(URLResolverTest.BaseUrl + relativeUrl)
    }

}