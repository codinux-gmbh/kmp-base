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
    fun urlEndsWithSlash() {
        val url = "https://codinux.net/path1/path2/"

        val result = URL(url)

        result.toString().shouldBe(url)
    }

    @Test
    fun urlDoesNotEndWithSlash() {
        val url = "https://codinux.net/path1/path2"

        val result = URL(url)

        result.toString().shouldBe(url)
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

    @Test
    fun relativeUrlIsDot_BaseUrlEndsWithSlash() {
        val baseUrl = "https://codinux.net/path1/path2/"

        val result = URL(baseUrl, ".")

        result.toString().shouldBe(baseUrl)
    }

    @Test
    fun relativeUrlIsDot_BaseUrlDoesNotEndWithSlash() {
        val baseUrl = "https://codinux.net/path1/path2"

        val result = URL(baseUrl, ".")

        result.toString().shouldBe("https://codinux.net/path1/")
    }

    @Test
    fun relativeUrlStartsWithDotAndSlash() {
        val relativeUrl = "./path4"

        val result = URL(URLResolverTest.BaseUrlWithPathQueryAndFragment, relativeUrl)

        // the last path segment (file) gets removed
        result.toString().shouldBe(URLResolverTest.BaseUrlWithPath.replace("/path3", "") + relativeUrl.substring(1))
    }

    @Test
    fun relativeUrlStartsWithDotSlashAndFile() {
        val baseUrl = "https://codinux.net"
        val relativeUrl = "./one/two.html"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe(baseUrl + relativeUrl.substring(1))
    }

    @Test
    fun baseUrlPathEndsWithSlash() {
        val baseUrl = "https://codinux.net/path1/path2/"
        val relativeUrl = "./one/two.html"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe(baseUrl + relativeUrl.substring(2))
    }

    @Test
    fun baseUrlPathDoesNotEndWithSlash() {
        val baseUrl = "https://codinux.net/path1/path2"
        val relativeUrl = "./one/two.html"

        val result = URL(baseUrl, relativeUrl)

        // the last path segment (file) gets removed
        result.toString().shouldBe("https://codinux.net/path1" + relativeUrl.substring(1))
    }


    @Test
    fun relativeUrlStartsWith1DotDotSlash() {
        val baseUrl = "https://codinux.net/path1/path2/path3/"
        val relativeUrl = "../one/two.html"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe("https://codinux.net/path1/path2" + relativeUrl.substring(2))
    }

    @Test
    fun relativeUrlStartsWith2DotDotSlash() {
        val baseUrl = "https://codinux.net/path1/path2/path3/"
        val relativeUrl = "../../one/two.html"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe("https://codinux.net/path1" + relativeUrl.substring(5))
    }

    @Test
    fun relativeUrlStartsWith3DotDotSlash() {
        val baseUrl = "https://codinux.net/path1/path2/path3/"
        val relativeUrl = "../../../one/two.html"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe("https://codinux.net" + relativeUrl.substring(8))
    }

    @Test
    fun relativeUrlStartsWith1MoreDotDotSlashThanBaseUrlHasPathSegments() {
        val baseUrl = "https://codinux.net/path1/path2/path3"
        val relativeUrl = "../../../one/two.html"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe("https://codinux.net" + relativeUrl.substring(8))
    }

    @Test
    fun relativeUrlStartsWith2MoreDotDotSlashThanBaseUrlHasPathSegments() {
        val baseUrl = "https://codinux.net/path1/path2"
        val relativeUrl = "../../../one/two.html"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe("https://codinux.net" + relativeUrl.substring(8))
    }


    @Test
    fun relativeUrlIsQuery_BaseUrlEndsWithPathSegment() {
        val baseUrl = "https://codinux.net/path1/path2/path3/"
        val relativeUrl = "?name1=value1&name2=value2"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe(baseUrl + relativeUrl)
    }

    @Test
    fun relativeUrlIsQuery_BaseUrlEndsWithFile() {
        val baseUrl = "https://codinux.net/path1/path2/path3"
        val relativeUrl = "?name1=value1&name2=value2"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe(baseUrl + relativeUrl)
    }

    @Test
    fun relativeUrlIsFragment_BaseUrlEndsWithPathSegment() {
        val baseUrl = "https://codinux.net/path1/path2/path3/"
        val relativeUrl = "#fragment"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe(baseUrl + relativeUrl)
    }

    @Test
    fun relativeUrlIsFragment_BaseUrlEndsWithFile() {
        val baseUrl = "https://codinux.net/path1/path2/path3"
        val relativeUrl = "#fragment"

        val result = URL(baseUrl, relativeUrl)

        result.toString().shouldBe(baseUrl + relativeUrl)
    }

}