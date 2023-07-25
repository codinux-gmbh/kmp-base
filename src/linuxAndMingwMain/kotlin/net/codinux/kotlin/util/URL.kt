package net.codinux.kotlin.util

actual class URL actual constructor(private val url: String) {

    actual constructor(baseUrl: String, relativeUrl: String) : this(URLResolver.resolveUrl(baseUrl, relativeUrl))


    private val urlParts = URLParser.Instance.parse(url)

    actual val scheme = urlParts.scheme

    actual val host = urlParts.host

    actual val port = urlParts.port


    override fun toString() = this.url

}