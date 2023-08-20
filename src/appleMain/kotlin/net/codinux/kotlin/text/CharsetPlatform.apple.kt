package net.codinux.kotlin.text

internal actual object CharsetPlatform {

    actual fun forName(charsetName: String): Charset? = null

}