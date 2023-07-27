package net.codinux.kotlin.util

import java.net.URL

actual class URL(private val impl: URL) {

    companion object {

        private fun adjustRelativeUrlForJavaBugs(buggyUrlPath: String, relativeUrl: String): String {
            // fix for: //example.com + ./foo = //example.com/./foo, not //example.com/foo
            var stringToRemove = ""
            var handledPath = buggyUrlPath

            while (handledPath.startsWith("../") || handledPath.startsWith("./")) {
                val indexOfSlash = handledPath.indexOf('/')
                stringToRemove += handledPath.substring(0, indexOfSlash + 1)
                handledPath = handledPath.substring(indexOfSlash + 1)
            }

            return relativeUrl.replaceFirst(stringToRemove, "")
        }
    }


    actual constructor(url: String) : this(try {
        URL(url)
    } catch (e: Throwable) {
        throw URLParser.createMalformedUrlException("'$url' is not an absolute URL")
    })

    actual constructor(baseUrl: String, relativeUrl: String) : this(try {
        val base = URL(baseUrl)
        URL(base, relativeUrl).let { url ->
            if (url.path.startsWith("./") || url.path.startsWith("/./") || url.path.startsWith("../") || url.path.startsWith("/../")) {
                val buggyPath = if (url.path.startsWith('/')) url.path.substring(1) else url.path
                URL(base, adjustRelativeUrlForJavaBugs(buggyPath, relativeUrl))
            } else if (relativeUrl.startsWith('?')) {
                URL(base, base.path + relativeUrl)// fix for: Java resolves '//path/file + ?foo' to '//path/?foo', not to '//path/file?foo'
            } else {
                url
            }
        }
    } catch (e: Throwable) {
        throw URLParser.createMalformedUrlException("'$baseUrl' is not an absolute URL")
    })


    actual val scheme: String = impl.protocol

    actual val host = impl.host.takeIf { it.isNotBlank() }

    actual val port = impl.port.takeIf { it > 0 }

    actual val path: String? = impl.path?.takeIf { it.isNotBlank() }?.let {
        it.substring(1) // remove leading '/'
            .takeIf { it.isNotBlank() }
    }

    actual val query: String? = impl.query

    actual val fragment: String? = impl.ref


    override fun toString(): String = impl.toExternalForm()

}