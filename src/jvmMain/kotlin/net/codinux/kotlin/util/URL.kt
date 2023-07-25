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

    actual val path: String? = impl.path?.takeIf { it.isNotBlank() }?.let {
        it.substring(1) // remove leading '/'
            .takeIf { it.isNotBlank() }
    }

    actual val query: String? = impl.query

    actual val fragment: String? = impl.ref


    override fun toString(): String = impl.toExternalForm()

}