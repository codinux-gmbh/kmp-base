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

}