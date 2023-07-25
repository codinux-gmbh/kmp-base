package net.codinux.kotlin.util

import platform.Foundation.NSURL

actual class URL(private val impl: NSURL) {

    companion object {

        private fun assertValidUrl(url: NSURL, urlString: String) {
            if (url.scheme == null) { // to be compatible with other platform's implementations (and as URLs have to start with protocol by standard)
                URLParser.throwMalformedUrlException("'$urlString' is not an absolute URL")
            }
        }

    }


    actual constructor(url: String) : this(NSURL(string = url).apply {
        assertValidUrl(this, url)
    })

    actual constructor(baseUrl: String, relativeUrl: String) : this(NSURL(string = relativeUrl, relativeToURL = NSURL(string = baseUrl)).apply {
        assertValidUrl(this, baseUrl)
    })

    actual val scheme = impl.scheme!! // we checked in constructor that scheme is not null

    actual val host = impl.host

    actual val port = impl.port?.intValue


    override fun toString() = impl.absoluteString!!

}