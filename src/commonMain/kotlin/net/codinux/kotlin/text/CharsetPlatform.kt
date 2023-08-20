package net.codinux.kotlin.text


internal expect object CharsetPlatform {

    val availableCharsets: Map<String, Charset>

    fun forName(charsetName: String): Charset?

}