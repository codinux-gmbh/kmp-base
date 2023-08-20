package net.codinux.kotlin.text


internal expect object CharsetPlatform {

    fun forName(charsetName: String): Charset?

}