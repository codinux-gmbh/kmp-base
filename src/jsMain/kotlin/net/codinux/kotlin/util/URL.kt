package net.codinux.kotlin.util

import org.w3c.dom.url.URL

actual class URL(private val impl: URL) {

    companion object {

        // if only ':/' is specified, JavaScript does not recognize that only path but no domain is set
        private fun needsAdjustmentForEmptyHost(url: String) =
            url.contains("://") == false && url.contains(":/")

        private fun adjustUrlToMakeJavaScriptUrlParsingWork(url: String): String {
            if (needsAdjustmentForEmptyHost(url)) {
                return url.replaceFirst(":/", "://127.0.0.1/")
            }

            return url
        }

    }


    private var protocolDefaultPortContainedInUrlString: Int? = null

    private var hostWasEmptyInUrlString = false

    actual constructor(url: String) : this(try {
        URL(adjustUrlToMakeJavaScriptUrlParsingWork(url))
    } catch (e: Throwable) {
        throw URLParser.createMalformedUrlException("'$url' is not an absolute URL")
    }) {
        assertValidUrl(url)
    }

    actual constructor(baseUrl: String, relativeUrl: String) : this(try {
        URL(relativeUrl, adjustUrlToMakeJavaScriptUrlParsingWork(baseUrl))
    } catch (e: Throwable) {
        throw URLParser.createMalformedUrlException("'$baseUrl' is not an absolute URL")
    }) {
        assertValidUrl(baseUrl)
    }

    private fun assertValidUrl(url: String) {
        // if only ':/' is specified, JavaScript does not recognize that only path but no domain is set
        if (needsAdjustmentForEmptyHost(url)) {
            hostWasEmptyInUrlString = true
        }

        // JavaScript removes the port from URL if it's the protocol default port
        URLParser.ProtocolDefaultPorts.forEach { (protocol, port) ->
            if (url.startsWith("$protocol:", true) && url.contains(":$port")) {
                // ... so add it again to be consistent on all platforms
                protocolDefaultPortContainedInUrlString = port
            }
        }
    }


    actual val scheme = impl.protocol.replace(":", "")

    actual val host: String?
        get() = if (hostWasEmptyInUrlString) {
            null
        } else {
            impl.host
        }

    actual val port: Int?
        get() = impl.port.toIntOrNull()
            ?: protocolDefaultPortContainedInUrlString

    actual val path: String? = impl.pathname.takeIf { it.isNotBlank() }?.let {
        it.substring(1) // remove leading '/'
            .takeIf { it.isNotBlank() }
    }

    actual val query: String? = impl.search.takeIf { it.isNotBlank() }?.let {
        it.substring(1) // remove leading '?'
    }

    actual val fragment: String? = impl.hash.takeIf { it.isNotBlank() }?.let {
        it.substring(1) // remove leading '#'
    }


    override fun toString() = impl.toString()

}