package net.codinux.kotlin.util

data class URLParts(
    val scheme: String,
    val host: String?,
    val port: Int?,
    val path: String? = null,
    val query: String? = null,
    val fragment: String? = null,
    val username: String? = null,
    val password: String? = null
)