package net.codinux.kotlin.util

class URLResolver {

    companion object {

        val Instance = URLResolver()

        fun resolveUrl(baseUrl: String, relativeUrl: String) = Instance.resolveUrl(baseUrl, relativeUrl)

    }


    fun resolveUrl(baseUrl: String, relativeUrl: String): String {
        if (isRelativeUrl(relativeUrl) == false) {
            throwNotARelativeUrlException(relativeUrl)
        }

        val baseParts = URLParser.Instance.parse(baseUrl)
        val baseUrlWithoutQueryAndFragment = baseUrl
            .replace(baseParts.query?.let { "?$it" } ?: "", "")
            .replace(baseParts.fragment?.let { "#$it" } ?: "", "")

        // TODO: implement removing file for most cases

        val baseUrlWithoutSlash = if (baseUrlWithoutQueryAndFragment.endsWith('/')) { // remove trailing slash
            baseUrlWithoutQueryAndFragment.substring(0, baseUrlWithoutQueryAndFragment.lastIndex)
        } else {
            baseUrlWithoutQueryAndFragment
        }
        val baseUrlWithSlash = if (baseUrlWithoutQueryAndFragment.endsWith('/')) { // remove trailing slash
            baseUrlWithoutQueryAndFragment
        } else {
            baseUrlWithoutQueryAndFragment + "/"
        }

        return resolveUrlAfterChecks(relativeUrl, baseUrlWithoutSlash, baseUrlWithSlash, baseParts.scheme)
    }

    private fun resolveUrlAfterChecks(relativeUrl: String, baseUrlWithoutSlash: String, baseUrlWithSlash: String, baseUrlScheme: String): String {
        // amongst others relative URLs can start with (see https://en.wikipedia.org/wiki/Uniform_Resource_Identifier#URI_references):
        // - // (domain relative url)
        // - /
        // - ./
        // - ../
        // - a letter (path or file)
        // - ? (query)
        // - # (fragment)
        // - ; (?)

        if (relativeUrl.isEmpty() || relativeUrl == "." || relativeUrl == "/") {
            return baseUrlWithSlash
        }

        val firstChar = relativeUrl[0] // empty relativeUrl is already handled above
        if (firstChar.isLetterOrDigit()) {
            return baseUrlWithSlash + relativeUrl
        }

        val secondChar = if (relativeUrl.length >= 2) relativeUrl[1] else null

        return when (firstChar) {
            '?', '#', ';' -> baseUrlWithoutSlash + relativeUrl // query or fragment
            '/' -> if (secondChar == '/') { // domain relative url
                baseUrlScheme + ":" + relativeUrl
            } else if (secondChar?.isLetterOrDigit() == true) { // after check above second char must be a letter or digit then -> path or file
                baseUrlWithoutSlash + relativeUrl
            } else { // should never come to this
                throwNotARelativeUrlException(relativeUrl)
            }
            '.' -> if (secondChar == '/') {
                baseUrlWithoutSlash + relativeUrl.substring(1)
            } else if (secondChar == '.') {
                resolveUrlByMovingUpPath(baseUrlWithSlash, relativeUrl)
            } else { // should never come to this
                throwNotARelativeUrlException(relativeUrl)
            }
            else -> throwNotARelativeUrlException(relativeUrl) // should never come to this
        }
    }

    private fun resolveUrlByMovingUpPath(baseUrlWithSlash: String, relativeUrl: String): String {
        throw Exception("Resolving relative URLs that start with '../' is not implemented yet")
    }

    fun isRelativeUrl(url: String): Boolean {
        if (url.isEmpty() || url == "." || url == "/") { // the trivial case
            return true
        }

        try {
            URLParser.Instance.extractScheme(url) // if the url has a scheme then it's no relative url
            return false
        } catch (ignored: Throwable) { }

        url.indexOfOrNull(":/")?.let {
            return false // relative URLs may not contain scheme separators
        }

        val secondChar = if (url.length >= 2) url[1] else null
        val thirdChar = if (url.length >= 3) url[2] else null

        return when (url[0]) { // it's checked above that url at this point is not empty; also "." and "/" have already been handled
            '?', '#' -> true // query or fragment
            '/' -> secondChar == '/' // domain relative url
                    || secondChar?.isLetterOrDigit() == true // path or file
            '.' -> secondChar == '/' || (secondChar == '.' && thirdChar == '/')
            ';' -> true // don't know what this means but it's listed on Wikipedia
            else -> url[0].isLetterOrDigit()
        }
    }

    private fun throwNotARelativeUrlException(relativeUrl: String): String {
        URLParser.throwMalformedUrlException("'$relativeUrl' is not a relative URL. Relative URLs start with '//', '/', a letter, './', '../', '?', or '#'")

        return "" // will never come to this due to exception thrown in throwMalformedUrlException(), just to make compiler happy with a virtual return type for when statements
    }

}