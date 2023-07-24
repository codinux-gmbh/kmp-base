package net.codinux.kotlin.util

fun String.indexOfOrNull(char: Char, startIndex: Int = 0, ignoreCase: Boolean = false): Int? =
    this.indexOf(char, startIndex, ignoreCase).takeIf { it > -1 }

fun String.lastIndexOfOrNull(char: Char, startIndex: Int = lastIndex, ignoreCase: Boolean = false): Int? =
    this.lastIndexOf(char, startIndex, ignoreCase).takeIf { it > -1 }

fun String.countChars(char: Char, startIndex: Int = 0, ignoreCase: Boolean = false): Int {
    var count = 0
    var nextIndex = this.indexOfOrNull(char, startIndex, ignoreCase)

    while (nextIndex != null) {
        count++
        nextIndex = this.indexOfOrNull(char, nextIndex + 1, ignoreCase)
    }

    return count
}