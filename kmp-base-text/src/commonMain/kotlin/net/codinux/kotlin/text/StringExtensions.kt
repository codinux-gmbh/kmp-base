package net.codinux.kotlin.text

fun String.Companion.fromCodePoint(codePoint: Int): String =
    Char.fromCodePoint(codePoint).concatToString()

fun String.getCodePointAt(index: Int): Int {
    val chars = this.toCharArray()
    val char = chars[index]

    return if (char.isHighSurrogate()) {
        val lowSurrogate = chars[index + 1]
        if (lowSurrogate.isLowSurrogate()) {
            (char.code shl 10) + lowSurrogate.code + -56613888
        } else {
            char.code
        }
    } else {
        char.code
    }
}