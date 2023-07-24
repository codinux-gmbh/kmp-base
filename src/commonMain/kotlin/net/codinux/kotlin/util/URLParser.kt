package net.codinux.kotlin.util

import net.codinux.kotlin.collections.ImmutableList
import net.codinux.kotlin.collections.ImmutableMap

/**
 * A very rudimentary version of a URL parser, not meant to be used in production.
 */
class URLParser {

    companion object {

        val Instance = URLParser()

        val KnownSchemes = ImmutableList("http", "https", "ftp", "mailto", "file", "data", "irc", "gopher")

        const val MaxPortNumber = 65535

        val IPv6Regex = Regex("""(?:[\da-fA-F]{0,4}:){1,7}(?:(?<ipv4>(?:(?:25[0-5]|2[0-4]\d|1?\d\d?)\.){3}(?:25[0-5]|2[0-4]\d|1?\d\d?))|[\da-f]{0,4})""")

        val ProtocolDefaultPorts = ImmutableMap(
            "http" to 80,
            "https" to 443,
            "ftp" to 20
        )

        fun createMalformedUrlException(message: String) = IllegalArgumentException(message)

        fun throwMalformedUrlException(message: String) {
            throw createMalformedUrlException(message)
        }

    }


    /**
     * We use the following syntax specification from [Wikipedia](https://en.wikipedia.org/wiki/URL#Syntax):
     *
     * The URI comprises:
     *
     * - A non-empty **scheme** component followed by a colon (:), consisting of a sequence of characters beginning with a letter and followed by any combination of letters, digits, plus (+), period (.), or hyphen (-). Although schemes are case-insensitive, the canonical form is lowercase and documents that specify schemes must do so with lowercase letters. Examples of popular schemes include http, https, ftp, mailto, file, data and irc. URI schemes should be registered with the Internet Assigned Numbers Authority (IANA), although non-registered schemes are used in practice.[b]
     *
     * - An optional **authority** component preceded by two slashes (//), comprising:
     *
     *     - An optional **userinfo** subcomponent followed by an at symbol (@), that may consist of a user name and an optional password preceded by a colon (:). Use of the format username:password in the userinfo subcomponent is deprecated for security reasons. Applications should not render as clear text any data after the first colon (:) found within a userinfo subcomponent unless the data after the colon is the empty string (indicating no password).
     *     - A **host** subcomponent, consisting of either a registered name (including but not limited to a hostname) or an IP address. IPv4 addresses must be in dot-decimal notation, and IPv6 addresses must be enclosed in brackets ([]).[16][c]
     *     - An optional **port** subcomponent preceded by a colon (:), consisting of decimal digits.
     *
     *  - A **path** component, consisting of a sequence of path segments separated by a slash (/). A path is always defined for a URI, though the defined path may be empty (zero length). A segment may also be empty, resulting in two consecutive slashes (//) in the path component. A path component may resemble or map exactly to a file system path but does not always imply a relation to one. If an authority component is defined, then the path component must either be empty or begin with a slash (/). If an authority component is undefined, then the path cannot begin with an empty segment—that is, with two slashes (//)—since the following characters would be interpreted as an authority component.[18]
     *
     *     By convention, in http and https URIs, the last part of a path is named pathinfo and it is optional. It is composed by zero or more path segments that do not refer to an existing physical resource name (e.g. a file, an internal module program or an executable program) but to a logical part (e.g. a command or a qualifier part) that has to be passed separately to the first part of the path that identifies an executable module or program managed by a web server; this is often used to select dynamic content (a document, etc.) or to tailor it as requested (see also: CGI and PATH_INFO, etc.).
     *     Example:
     *
     *         URI: "http://www.example.com/questions/3456/my-document"
     *         where: "/questions" is the first part of the path (an executable module or program) and "/3456/my-document" is the second part of the path named pathinfo, which is passed to the executable module or program named "/questions" to select the requested document.
     *
     *     An http or https URI containing a pathinfo part without a query part may also be referred to as a 'clean URL' whose last part may be a 'slug'.
     *
     * Query delimiter 	Example
     * Ampersand (&) 	key1=value1&key2=value2
     * Semicolon (;)[d] 	key1=value1;key2=value2
     *
     * - An optional **query** component preceded by a question mark (?), consisting of a query string of non-hierarchical data. Its syntax is not well defined, but by convention is most often a sequence of attribute–value pairs separated by a delimiter.
     * - An optional **fragment** component preceded by a hash (#). The fragment contains a fragment identifier providing direction to a secondary resource, such as a section heading in an article identified by the remainder of the URI. When the primary resource is an HTML document, the fragment is often an id attribute of a specific element, and web browsers will scroll this element into view.
     */
    fun parse(url: String): URLParts {
        val indexOfFirstColon = url.indexOfOrNull(':')
        if (indexOfFirstColon == null) {
            throwMalformedUrlException("URL must start with the protocol followed by a colon like 'https:'")
        }

        val scheme = url.substring(0, indexOfFirstColon!!)
        if (KnownSchemes.none { it.equals(scheme, true) }) {
            throwMalformedUrlException("'$scheme' is not a known scheme. Known schemes are: $KnownSchemes")
        }

        if (url.length == indexOfFirstColon + 1 || url[indexOfFirstColon + 1] != '/') {
            throwMalformedUrlException("The scheme ('$scheme:') has to be followed by a '/'")
        }

        val indexOfFirstSlash = indexOfFirstColon + 1

        return if (url.length == indexOfFirstSlash + 1) { // no host and empty path
            URLParts(scheme, null, null, "")
        } else if (url[indexOfFirstSlash + 1] == '/') {
            if (url.length == indexOfFirstSlash + 2) {
                throwMalformedUrlException("After '://' a host has to be specified")
            }
            parseAuthorityAndPath(scheme, url.substring(indexOfFirstSlash + 2))
        } else {
            parsePath(scheme, url.substring(indexOfFirstSlash + 1))
        }
    }

    private fun parseAuthorityAndPath(scheme: String, authorityAndPath: String): URLParts {
        val pathStartIndex = authorityAndPath.indexOfOrNull('/') ?: authorityAndPath.length
        val authority = authorityAndPath.substring(0, pathStartIndex)

        if (authority.any { isInvalidAuthorityChar(it) }) {
            throwMalformedUrlException("The authority part ('$authority') may only contain alphanumerical chars, '.', '[', ']', '@' and ':'")
        }

        val ipv6Address = checkIfContainsValidIpv6Address(authority)

        val indexOfAt = authorityAndPath.indexOfOrNull('@')
        val usernameAndPassword = indexOfAt?.let { authorityAndPath.substring(0, indexOfAt) }
        val indexOfPasswordSeparator = usernameAndPassword?.let { it.indexOfOrNull(':') }
        val username = usernameAndPassword?.let {
            if (indexOfPasswordSeparator != null) it.substring(0, indexOfPasswordSeparator) else it
        }
//        if (username?.any { it.isLetterOrDigit() } == false) {
//            throwMalformedUrlException("The username '$username' - all text between the scheme and the '@' symbol - may only contain alphanumerical symbols")
//        }

        val password = indexOfPasswordSeparator?.let { authorityAndPath.substring(indexOfPasswordSeparator + 1, indexOfAt) }

        val hostAndPort = authorityAndPath.substring(indexOfAt?.let { it + 1 } ?: 0)
        if (ipv6Address != null && hostAndPort.startsWith("[$ipv6Address]") == false) {
            throwMalformedUrlException("The IPv6 address '$ipv6Address' must be right at the beginning of the host part: $hostAndPort")
        }

        val indexOfPortSeparator = if (ipv6Address != null) hostAndPort.indexOfOrNull(':', ipv6Address.length) else hostAndPort.lastIndexOfOrNull(':')
        if (ipv6Address != null && indexOfPortSeparator != null && indexOfPortSeparator != ipv6Address.length + 2) {
            throwMalformedUrlException("The port has to be stated after a colon right after the IPv6 address like '[::1]:8080'. Error at: '$hostAndPort'")
        }
        val port = indexOfPortSeparator?.let {
            val portString = hostAndPort.substring(it + 1)
            val port = mapToValidToPort(portString)
            if (port == null) {
                throwMalformedUrlException("The port - all characters after ':' and before the path - '$portString' is not a valid port between 0-$MaxPortNumber")
            }
            port
        }

        val host = if (ipv6Address != null) {
            ipv6Address
        } else if (indexOfPortSeparator != null) {
            hostAndPort.substring(0, indexOfPortSeparator)
        } else {
            hostAndPort
        }

        val path = if (pathStartIndex == authorityAndPath.length) "" else authorityAndPath.substring(pathStartIndex)

        return parsePath(scheme, path, host, port, username, password)
    }

    private fun parsePath(scheme: String, path: String, host: String? = null, port: Int? = null, username: String? = null, password: String? = null): URLParts {
        //TODO: parse query and fragment

        return URLParts(scheme, host, port, path, null, null, username, password)
    }

    private fun checkIfContainsValidIpv6Address(authority: String): String? {
        val indexOfOpeningBracket = authority.indexOfOrNull('[')
        val indexOfClosingBracket = authority.indexOfOrNull(']')

        if (indexOfOpeningBracket == null && indexOfClosingBracket == null) {
            return null
        }

        if (indexOfOpeningBracket != null && indexOfClosingBracket == null) {
            throwMalformedUrlException("Authority contains '[' but no ']': $authority")
        }
        if (indexOfOpeningBracket == null && indexOfClosingBracket != null) {
            throwMalformedUrlException("Authority contains ']' but no '[': $authority")
        }

        if (authority.countChars('[') > 1) {
            throwMalformedUrlException("Authority may only contain one '[': $authority")
        }
        if (authority.countChars(']') > 1) {
            throwMalformedUrlException("Authority may only contain one ']': $authority")
        }

        val ipv6Candidate = authority.substring(indexOfOpeningBracket!! + 1, indexOfClosingBracket!!)
        if (isValidIpv6Address(ipv6Candidate) == false) {
            throwMalformedUrlException("'$ipv6Candidate' is not a valid IPv6 address")
        }

        return ipv6Candidate
    }

    private fun isValidIpv6Address(ipv6Candidate: String): Boolean =
        IPv6Regex.matches(ipv6Candidate)

    private fun mapToValidToPort(portString: String): Int? {
        val portCandidate = portString.toIntOrNull()
        return portCandidate?.takeIf { portCandidate in 0..MaxPortNumber }
    }

    private fun isInvalidAuthorityChar(char: Char) = isValidAuthorityChar(char) == false

    private fun isValidAuthorityChar(char: Char): Boolean =
        char.isLetterOrDigit() || char == '.' || char == ':' || char == '@' || char == '[' || char == ']'

}