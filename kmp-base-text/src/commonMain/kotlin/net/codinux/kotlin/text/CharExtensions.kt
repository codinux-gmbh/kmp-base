package net.codinux.kotlin.text

const val MinSupplementaryCodePoint = 65536 // = all codepoints for which two bytes are not sufficient to encode them

const val MaxCodePoint = 0X10FFFF

fun Char.Companion.fromCodePoint(codePoint: Int): CharArray =
    // code taken from Kotlin Native Char.Companion.toChars(codePoint: Int): CharArray
    when (codePoint) {
        in 0 until MinSupplementaryCodePoint -> charArrayOf(codePoint.toChar())
        in MinSupplementaryCodePoint..MaxCodePoint -> {
            val low = ((codePoint - 0x10000) and 0x3FF) + Char.MIN_LOW_SURROGATE.code
            val high = (((codePoint - 0x10000) ushr 10) and 0x3FF) + Char.MIN_HIGH_SURROGATE.code
            charArrayOf(high.toChar(), low.toChar())
        }
        else -> throw IllegalArgumentException()
    }