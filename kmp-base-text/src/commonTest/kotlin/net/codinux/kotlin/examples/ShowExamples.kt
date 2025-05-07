package net.codinux.kotlin.examples

import net.codinux.kotlin.text.Charset
import net.codinux.kotlin.text.Charsets

class ShowExamples {

    fun charsets() {
        // standard charsets:
        Charsets.UTF8 // also .UTF16_LE, .US_ASCII, .ISO_8859_1, ...

        // charsets available on platform:
        Charset.availableCharsets

        // charset for name
        Charset.forName("UTF-8")
    }

}