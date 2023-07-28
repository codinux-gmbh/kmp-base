package net.codinux.kotlin.util

data class URLParts(
    val schemeAsInOriginalString: String,
    val authority: String?,
    val host: String?,
    val port: Int?,
    val path: String? = null,
    val file: String? = null,
    val query: String? = null,
    val fragment: String? = null,
    val username: String? = null,
    val password: String? = null,
    val hostIsIPv6Address: Boolean = false
) {


    /**
     * According to [RFC-3987](https://datatracker.ietf.org/doc/html/rfc3986#section-3.1):
     * "Although schemes are case-insensitive, the canonical form is lowercase and documents that specify schemes
     * must do so with lowercase letters.  An implementation should accept uppercase letters as equivalent to
     * lowercase in scheme names (e.g., allow "HTTP" as well as "http") for the sake of robustness but should only
     * produce lowercase scheme names for consistency."
     */
    val scheme = schemeAsInOriginalString.lowercase()

}