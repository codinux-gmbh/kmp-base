package net.codinux.kotlin.util

actual class URL actual constructor(private val url: String) {

    actual constructor(baseUrl: String, relativeUrl: String) : this(URLResolver.resolveUrl(baseUrl, relativeUrl))


    private val urlParts = URLParser.Instance.parse(url)

    actual val scheme = urlParts.scheme

    actual val host = urlParts.host

    actual val port = urlParts.port

    actual val path: String? = urlParts.path

    actual val query = urlParts.query

    actual val fragment = urlParts.fragment


    override fun toString() = this.url.replace(urlParts.schemeAsInOriginalString, urlParts.scheme)

}