@file:OptIn(ExperimentalContracts::class)

package net.codinux.kotlin.text

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

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


fun String.indexOfOrNull(char: Char, startIndex: Int = 0, ignoreCase: Boolean = false): Int? =
    this.indexOf(char, startIndex, ignoreCase).takeIf { it > -1 }

fun String.indexOfOrNull(char: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? =
    this.indexOf(char, startIndex, ignoreCase).takeIf { it > -1 }

fun String.lastIndexOfOrNull(char: Char, startIndex: Int = lastIndex, ignoreCase: Boolean = false): Int? =
    this.lastIndexOf(char, startIndex, ignoreCase).takeIf { it > -1 }

fun String.lastIndexOfOrNull(char: String, startIndex: Int = lastIndex, ignoreCase: Boolean = false): Int? =
    this.lastIndexOf(char, startIndex, ignoreCase).takeIf { it > -1 }

fun String.countOccurrences(char: Char, startIndex: Int = 0, ignoreCase: Boolean = false): Int {
    var count = 0
    var nextIndex = this.indexOfOrNull(char, startIndex, ignoreCase)

    while (nextIndex != null) {
        count++
        nextIndex = this.indexOfOrNull(char, nextIndex + 1, ignoreCase)
    }

    return count
}

fun String.countOccurrences(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int {
    var count = 0
    var nextIndex = this.indexOfOrNull(string, startIndex, ignoreCase)

    while (nextIndex != null) {
        count++
        nextIndex = this.indexOfOrNull(string, nextIndex + 1, ignoreCase)
    }

    return count
}

inline fun CharSequence?.isNotNullOrEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrEmpty != null)
    }

    return this.isNullOrEmpty() == false
}

inline fun CharSequence?.isNotNullOrBlank(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrBlank != null)
    }

    return this.isNullOrBlank() == false
}