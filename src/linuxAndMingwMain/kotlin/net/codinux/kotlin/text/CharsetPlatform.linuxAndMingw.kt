package net.codinux.kotlin.text

import net.codinux.collections.immutableMapOf

internal actual object CharsetPlatform {

    actual val availableCharsets: Map<String, Charset> = immutableMapOf()

    actual fun forName(charsetName: String): Charset? = null

}