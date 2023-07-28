package net.codinux.kotlin.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class URLParserTest {

    private val underTest = URLParser()


    @Test
    fun schemeWithoutColon() {
        shouldThrow<IllegalArgumentException> {
            underTest.parse("http")
        }
    }

    @Test
    fun schemeOnly() {
        shouldThrow<IllegalArgumentException> {
            underTest.parse("http:")
        }
    }

    @Test
    fun unknownScheme() {
        val url = "tel:867-5309"

        val result = underTest.parse(url)

        assertUrlParts(result, "tel", null, "867-5309")
    }

    @Test
    fun isUppercase() {
        val url = "HTTPS://codinux.net"

        val result = underTest.parse(url)

        assertUrlParts(result, "https", "codinux.net")
    }

    @Test
    fun unknownSchemeIsUppercase() {
        val url = "TEL:867-5309"

        val result = underTest.parse(url)

        assertUrlParts(result, "tel", null, "867-5309")
    }


    @Test
    fun schemeWithEmptyPath() {
        val result = underTest.parse("http:/")

        assertUrlParts(result, "http", null)
    }

    @Test
    fun schemeAndPath() {
        val result = underTest.parse("http:/index.html")

        assertUrlParts(result, "http", null, "index.html")
    }


    @Test
    fun schemeAndDoubleSlashWithoutHost() {
        shouldThrow<IllegalArgumentException> {
            underTest.parse("http://")
        }
    }

    @Test
    fun schemeAndLocalhost() {
        val result = underTest.parse("https://localhost")

        assertUrlParts(result, "https", "localhost")
    }

    @Test
    fun schemeAndDomainNameWithoutTLD() {
        val result = underTest.parse("https://codinux") // TODO: add check that TLD is missing

        assertUrlParts(result, "https", "codinux")
    }

    @Test
    fun schemeAndHost() {
        val result = underTest.parse("https://codinux.net:5432")

        assertUrlParts(result, "https", "codinux.net", port = 5432)
    }

    @Test
    fun schemeHostAndSubdomain() {
        val result = underTest.parse("https://www.codinux.net")

        assertUrlParts(result, "https", "www.codinux.net")
    }

    @Test
    fun schemeHostSubdomainAndPort() {
        val result = underTest.parse("https://staging.codinux.net:9092")

        assertUrlParts(result, "https", "staging.codinux.net", port = 9092)
    }


    @Test
    fun ipv4Loopback() {
        val result = underTest.parse("http://127.0.0.0")

        assertUrlParts(result, "http", "127.0.0.0")
    }

    @Test
    fun ipv4LoopbackAndPort() {
        val result = underTest.parse("http://127.0.0.0:8080")

        assertUrlParts(result, "http", "127.0.0.0", port = 8080)
    }


    @Test
    fun invalidIPv6ClosingBracketIsMissing() {
        shouldThrow<IllegalArgumentException> {
            underTest.parse("http://[::1")
        }
    }

    @Test
    fun invalidIPv6_OpeningBracketIsMissing() {
        shouldThrow<IllegalArgumentException> {
            underTest.parse("http://::1]")
        }
    }

    @Test
    fun invalidIPv6_TwoClosingBrackets() {
        shouldThrow<IllegalArgumentException> {
            underTest.parse("http://[::1]]")
        }
    }

    @Test
    fun invalidIPv6TwoOpeningBrackets() {
        shouldThrow<IllegalArgumentException> {
            underTest.parse("http://[::1][")
        }
    }

    @Test
    fun invalidIPv6_IPv4InSquareBrackets() {
        shouldThrow<IllegalArgumentException> {
            underTest.parse("http://[192.168.0.1]")
        }
    }

    @Test
    fun ipv6Loopback() {
        val result = underTest.parse("http://[::1]")

        assertUrlParts(result, "http", "::1", hostIsIPv6Address = true)
    }

    @Test
    fun ipv6FullLoopback() {
        val result = underTest.parse("http://[0000:0000:0000:0000:0000:0000:0000:0001]")

        assertUrlParts(result, "http", "0000:0000:0000:0000:0000:0000:0000:0001", hostIsIPv6Address = true)
    }

    @Test
    fun ipv6() {
        val result = underTest.parse("http://[fe80::20c:29ff:fee2:1de]")

        assertUrlParts(result, "http", "fe80::20c:29ff:fee2:1de", hostIsIPv6Address = true)
    }

    @Test
    fun ipv6_2() {
        val result = underTest.parse("http://[2001:0db8:85a3:0000:0000:8a2e:0370:7334]")

        assertUrlParts(result, "http", "2001:0db8:85a3:0000:0000:8a2e:0370:7334", hostIsIPv6Address = true)
    }

    @Test
    fun ipv6_3() {
        val result = underTest.parse("http://[FE80:0000:0000:0000:0202:B3FF:FE1E:8329]")

        assertUrlParts(result, "http", "FE80:0000:0000:0000:0202:B3FF:FE1E:8329", hostIsIPv6Address = true)
    }

    @Test
    fun ipv6AndPort() {
        val result = underTest.parse("http://[fe80::20c:29ff:fee2:1de]:8080")

        assertUrlParts(result, "http", "fe80::20c:29ff:fee2:1de", port = 8080, hostIsIPv6Address = true)
    }

    @Test
    fun ipv6IllegalCharacterBeforePort() {
        shouldThrow<IllegalArgumentException> {
            underTest.parse("http://[fe80::20c:29ff:fee2:1de]1:8080")
        }
    }


    @Test
    fun usernameAndHost() {
        val result = underTest.parse("https://user27@staging.codinux.net")

        assertUrlParts(result, "https", "staging.codinux.net", username = "user27")
    }

    @Test
    fun usernamePasswordAndHost() {
        val result = underTest.parse("https://user27:admin123@staging.codinux.net")

        assertUrlParts(result, "https", "staging.codinux.net", username = "user27", password = "admin123")
    }

    @Test
    fun usernameHostAndPort() {
        val result = underTest.parse("https://user27@staging.codinux.net:5432")

        assertUrlParts(result, "https", "staging.codinux.net", port = 5432, username = "user27")
    }

    @Test
    fun usernamePasswordHostAndPort() {
        val result = underTest.parse("https://user27:admin123@staging.codinux.net:5432")

        assertUrlParts(result, "https", "staging.codinux.net", port = 5432, username = "user27", password = "admin123")
    }

    @Test
    fun usernameAndIPv4() {
        val result = underTest.parse("http://xerxes@192.168.0.12:4321")

        assertUrlParts(result, "http", "192.168.0.12", port = 4321, username = "xerxes")
    }

    @Test
    fun usernamePasswordAndIPv4() {
        val result = underTest.parse("http://xerxes:salamis@192.168.0.12:4321")

        assertUrlParts(result, "http", "192.168.0.12", port = 4321, username = "xerxes", password = "salamis")
    }


    @Test
    fun pathWithoutFile() {
        val result = underTest.parse("https://www.codinux.net/path1/path2/")

        assertUrlParts(result, "https", "www.codinux.net", "path1/path2/")
    }

    @Test
    fun pathWithFile() {
        val result = underTest.parse("https://www.codinux.net/path1/path2/index.html")

        assertUrlParts(result, "https", "www.codinux.net", "path1/path2/", file = "index.html")
    }

    @Test
    fun pathDoesNotEndWithSlash_LastSegmentGetsTreatedAsFile() {
        val result = underTest.parse("https://www.codinux.net/path1/path2")

        assertUrlParts(result, "https", "www.codinux.net", "path1/", "path2")
    }

    @Test
    fun query() {
        val result = underTest.parse("https://www.codinux.net/path/?name1=value1&name2=value2")

        assertUrlParts(result, "https", "www.codinux.net", "path/", query = "name1=value1&name2=value2")
    }

    @Test
    fun fragment() {
        val result = underTest.parse("https://www.codinux.net/path/#fragment")

        assertUrlParts(result, "https", "www.codinux.net", "path/", fragment = "fragment")
    }

    @Test
    fun queryBeforeFragment() {
        val result = underTest.parse("https://www.codinux.net/path/?name1=value1&name2=value2#fragment")

        assertUrlParts(result, "https", "www.codinux.net", "path/", query = "name1=value1&name2=value2", fragment = "fragment")
    }

    @Test
    fun fragmentBeforeQuery() { // actually an illegal URL, but check if URLParser can handle it
        val result = underTest.parse("https://www.codinux.net/path/#fragment?name1=value1&name2=value2")

        assertUrlParts(result, "https", "www.codinux.net", "path/", query = "name1=value1&name2=value2", fragment = "fragment")
    }


    private fun assertUrlParts(result: URLParts, scheme: String, host: String?, path: String? = null, file: String? = null, query: String? = null, fragment: String? = null,
                               port: Int? = null, username: String? = null, password: String? = null, hostIsIPv6Address: Boolean = false) {
        result.scheme.shouldBe(scheme)

        result.host.shouldBe(host)
        result.port.shouldBe(port)

        result.path.shouldBe(path)
        result.file.shouldBe(file)

        result.query.shouldBe(query)
        result.fragment.shouldBe(fragment)

        result.username.shouldBe(username)
        result.password.shouldBe(password)

        result.hostIsIPv6Address.shouldBe(hostIsIPv6Address)

        if (host != null) {
            var expectedAuthority = if (hostIsIPv6Address) "[$host]" else host
            if (port != null) {
                expectedAuthority += ":$port"
            }
            if (username != null) {
                if (password == null) {
                    expectedAuthority = "$username@$expectedAuthority"
                } else {
                    expectedAuthority = "$username:$password@$expectedAuthority"
                }
            }

            result.authority.shouldBe(expectedAuthority)
        }
    }
}