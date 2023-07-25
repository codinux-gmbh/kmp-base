package net.codinux.kotlin.util

import java.net.URL

actual class URL(private val impl: URL) {

    actual constructor(url: String) : this(try {
        URL(url)
    } catch (e: Throwable) {
        throw URLParser.createMalformedUrlException("'$url' is not an absolute URL")
    })

    actual constructor(baseUrl: String, relativeUrl: String) : this(try {
        URL(URL(baseUrl), relativeUrl)
    } catch (e: Throwable) {
        throw URLParser.createMalformedUrlException("'$baseUrl' is not an absolute URL")
    })


    actual val scheme: String = impl.protocol

    actual val host = impl.host.takeIf { it.isNotBlank() }

    actual val port = impl.port.takeIf { it > 0 }


    override fun toString(): String = impl.toExternalForm()

}