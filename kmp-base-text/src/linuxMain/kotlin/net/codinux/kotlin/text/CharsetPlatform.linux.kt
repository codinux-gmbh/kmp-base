package net.codinux.kotlin.text

internal actual object CharsetPlatform {

    actual val availableCharsets: Map<String, Charset> = emptyMap()

    actual fun forName(charsetName: String): Charset? = null

}