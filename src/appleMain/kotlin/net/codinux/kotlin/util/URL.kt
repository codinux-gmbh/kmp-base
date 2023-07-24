package net.codinux.kotlin.util

import platform.Foundation.NSURL

actual class URL(private val impl: NSURL) {

    actual constructor(url: String) : this(NSURL(string = url).apply {
        if (this.scheme == null) { // to be compatible with other platform's implementations (and as URLs have to start with protocol by standard)
            URLParser.throwMalformedUrlException("'$url' is not an absolute URL")
        }
    })

    actual val scheme = impl.scheme!! // we checked in constructor that scheme is not null

    actual val host = impl.host

    actual val port = impl.port?.intValue

}