package net.codinux.kotlin.util

import net.codinux.kotlin.text.indexOfOrNull
import net.codinux.kotlin.text.lastIndexOfOrNull

class URLResolver {

    companion object {

        val Instance = URLResolver()

        fun resolveUrl(baseUrl: String, relativeUrl: String) = Instance.resolveUrl(baseUrl, relativeUrl)

    }


    /**
     * Resolves a relative URL to a base URL given that rules from [Wikipedia](https://en.wikipedia.org/wiki/Uniform_Resource_Identifier#URI_references):
     *
     * Given the base URI:
     * ```
     * http://a/b/c/d;p?q
     * ```
     *
     * then:
     * ```
     * "g:h"     -> "g:h"
     * "g"       -> "http://a/b/c/g"
     * "./g"     -> "http://a/b/c/g"
     * "g/"      -> "http://a/b/c/g/"
     * "/g"      -> "http://a/g"
     * "//g"     -> "http://g"
     * "?y"      -> "http://a/b/c/d;p?y"
     * "g?y"     -> "http://a/b/c/g?y"
     * "#s"      -> "http://a/b/c/d;p?q#s"
     * "g#s"     -> "http://a/b/c/g#s"
     * "g?y#s"   -> "http://a/b/c/g?y#s"
     * ";x"      -> "http://a/b/c/;x"
     * "g;x"     -> "http://a/b/c/g;x"
     * "g;x?y#s" -> "http://a/b/c/g;x?y#s"
     * ""        -> "http://a/b/c/d;p?q"
     * "."       -> "http://a/b/c/"
     * "./"      -> "http://a/b/c/"
     * ".."      -> "http://a/b/"
     * "../"     -> "http://a/b/"
     * "../g"    -> "http://a/b/g"
     * "../.."   -> "http://a/"
     * "../../"  -> "http://a/"
     * "../../g" -> "http://a/g"
     * ```
     */
    fun resolveUrl(baseUrl: String, relativeUrl: String): String {
        if (relativeUrl.isEmpty()) { // the trivial case
            return baseUrl
        }

        if (isRelativeUrl(relativeUrl) == false) {
            throwNotARelativeUrlException(relativeUrl)
        }

        val baseParts = URLParser.Instance.parse(baseUrl)

        return resolveUrl(baseUrl, baseParts, relativeUrl)
    }

    private fun resolveUrl(originalBaseUrl: String, baseUrlParts: URLParts, relativeUrl: String): String {
        // amongst others relative URLs can start with (see https://en.wikipedia.org/wiki/Uniform_Resource_Identifier#URI_references):
        // - // (domain relative url)
        // - /
        // - ./
        // - ../
        // - a letter (path or file)
        // - ? (query)
        // - # (fragment)
        // - ; (?)

        val baseUrl = "${baseUrlParts.scheme}:" + (
                baseUrlParts.authority?.let { "//${baseUrlParts.authority}" } ?: "")
        val baseUrlWithPath = "$baseUrl${baseUrlParts.path?.let { "/$it" } ?: ""}".let {
            if (it.endsWith('/')) it.substring(0, it.lastIndex) else it
        }

        if (relativeUrl.isEmpty() || relativeUrl == "." || relativeUrl == "/") {
            return baseUrlWithPath + "/"
        }

        val firstChar = relativeUrl[0] // empty relativeUrl is already handled above
        if (firstChar.isLetterOrDigit()) {
            return baseUrlWithPath + "/" + relativeUrl
        }

        val secondChar = if (relativeUrl.length >= 2) relativeUrl[1] else null
        val thirdChar = if (relativeUrl.length >= 3) relativeUrl[2] else null

        return when (firstChar) {
            '?', '#', ';' -> originalBaseUrl + relativeUrl // query or fragment
            '/' -> if (secondChar == '/') { // // = domain relative url
                baseUrlParts.scheme + ":" + relativeUrl
            } else if (secondChar?.isLetterOrDigit() == true) { // after check above second char must be a letter or digit then -> path or file
                baseUrl + relativeUrl
            } else if (secondChar == '.') { // /.
                baseUrl + relativeUrl.replace("../", "").replace("./", "")
            } else { // should never come to this
                throwNotARelativeUrlException(relativeUrl)
            }
            '.' -> if (secondChar == '/') { // ./
                resolveUrlByMovingUpPath(baseUrlWithPath, relativeUrl)
            } else if (secondChar == '.') { // ..
                if (thirdChar == '/') { // ../
                    resolveUrlByMovingUpPath(baseUrlWithPath, relativeUrl)
                } else { // ..g
                    baseUrlWithPath + "/" + relativeUrl
                }
            } else if (secondChar?.isLetterOrDigit() == true) { // .g
                baseUrlWithPath + "/" + relativeUrl
            } else if (secondChar == null) { // .
                baseUrlWithPath
            } else { // should never come to this
                throwNotARelativeUrlException(relativeUrl)
            }
            else -> throwNotARelativeUrlException(relativeUrl) // should never come to this
        }
    }

    private fun resolveUrlByMovingUpPath(baseUrl: String, relativeUrl: String): String {
        var handledBaseUrl = baseUrl
        var handledRelativeUrl = relativeUrl
        val schemeSeparatorIndex = baseUrl.indexOfOrNull("://")?.let { it + 2 }
            ?: baseUrl.indexOfOrNull(":/")?.let { it + 1 }

        while (handledRelativeUrl.startsWith("./") || handledRelativeUrl.startsWith("../")) {
            if (handledRelativeUrl.startsWith("./")) {
                handledRelativeUrl = handledRelativeUrl.substring(2)
            } else if (handledRelativeUrl.startsWith("../")) {
                val lastIndexOfSlash = handledBaseUrl.lastIndexOfOrNull('/')
                if (lastIndexOfSlash != null && lastIndexOfSlash != schemeSeparatorIndex) { // only remove path segments if there are still path segments left in handledBaseUrl; but as Java and JS do not throw an exception we also don't
                    handledBaseUrl = handledBaseUrl.substring(0, lastIndexOfSlash)
                }

                handledRelativeUrl = handledRelativeUrl.substring(3)
            }
        }

        return handledBaseUrl + "/" + handledRelativeUrl
    }

    fun isRelativeUrl(url: String): Boolean {
        if (url.isEmpty() || url == "." || url == "/") { // the trivial cases
            return true
        }

        val secondChar = if (url.length >= 2) url[1] else null
        val thirdChar = if (url.length >= 3) url[2] else null

        return when (url[0]) { // it's checked above that url at this point is not empty; also "." and "/" have already been handled
            '?', '#' -> true // query or fragment
            '/' -> secondChar == '/' // domain relative url
                    || secondChar?.isLetterOrDigit() == true // path or file
                    || (secondChar == '.' && (thirdChar == '/' || thirdChar == '.'))
            '.' -> secondChar == '/' || isValidPathCharacter(secondChar) ||
                    (secondChar == '.' && (thirdChar == '/' || isValidPathCharacter(thirdChar)))
            ';' -> true // don't know what this means but it's listed on Wikipedia
            else -> url[0].isLetterOrDigit()
        }
    }

    private fun isValidPathCharacter(char: Char?) =
        char != null && char.isLetterOrDigit() // TODO: allow all valid characters

    private fun throwNotARelativeUrlException(relativeUrl: String): String =
        URLParser.throwMalformedUrlExceptionWithReturnType("'$relativeUrl' is not a relative URL. Relative URLs start with '//', '/', a letter, './', '../', '?', or '#'")

}