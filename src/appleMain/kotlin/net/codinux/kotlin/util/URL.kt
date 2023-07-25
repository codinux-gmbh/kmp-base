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


    private var _path: String? = null

    actual constructor(url: String) : this(NSURL(string = url).apply {
        assertValidUrl(this, url)
    }) {
        remedyValue(url)
    }

    actual constructor(baseUrl: String, relativeUrl: String) : this(NSURL(string = relativeUrl, relativeToURL = NSURL(string = baseUrl)).apply {
        assertValidUrl(this, baseUrl)
    }) {
        remedyValue(relativeUrl)
    }

    private fun remedyValue(url: String) {
        _path = impl.path?.takeIf { it.isNotBlank() }?.let {
            it.substring(1) // remove leading '/'
                .takeIf { it.isNotBlank() }
        }

        if (_path.isNotNullOrBlank()) {
            if (url.endsWith('/') || url.contains("/?") || url.contains("#/")) { // path originally ended with a slash ...
                _path += "/" // but NSURL removes this trailing slash, so add it again to be consistent with other platforms' implementation
            }
        }
    }


    actual val scheme = impl.scheme!! // we checked in constructor that scheme is not null

    actual val host = impl.host

    actual val port = impl.port?.intValue

    actual val path: String?
        get() = _path

    actual val query = impl.query

    actual val fragment = impl.fragment


    override fun toString() = impl.absoluteString!!

}