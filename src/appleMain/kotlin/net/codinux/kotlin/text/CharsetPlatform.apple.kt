package net.codinux.kotlin.text

import net.codinux.collections.immutableMapOf

internal actual object CharsetPlatform {

    // TODO implement. These links may contain useful information to implement this:
    // https://developer.apple.com/documentation/swift/string/encoding
    // https://developer.apple.com/documentation/swift/unicode
    actual val availableCharsets: Map<String, Charset> = immutableMapOf()

    actual fun forName(charsetName: String): Charset? = null

}