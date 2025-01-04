package net.codinux.kotlin.extensions

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


@OptIn(ExperimentalContracts::class)
inline fun CharSequence?.isNotNullOrEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrEmpty != null)
    }

    return this.isNullOrEmpty() == false
}

@OptIn(ExperimentalContracts::class)
inline fun CharSequence?.isNotNullOrBlank(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrBlank != null)
    }

    return this.isNullOrBlank() == false
}


fun CharSequence.indexOfOrNull(char: Char, startIndex: Int = 0, ignoreCase: Boolean = false) =
    this.indexOf(char, startIndex, ignoreCase).takeIf { it != -1 }

fun CharSequence.indexOfOrNull(string: String, startIndex: Int = 0, ignoreCase: Boolean = false) =
    this.indexOf(string, startIndex, ignoreCase).takeIf { it != -1 }

fun CharSequence.lastIndexOfOrNull(char: Char, startIndex: Int = lastIndex, ignoreCase: Boolean = false) =
    this.lastIndexOf(char, startIndex, ignoreCase).takeIf { it != -1 }

fun CharSequence.lastIndexOfOrNull(string: String, startIndex: Int = lastIndex, ignoreCase: Boolean = false) =
    this.lastIndexOf(string, startIndex, ignoreCase).takeIf { it != -1 }


fun String.substringAfterOrNull(delimiter: Char): String? = if (this.contains(delimiter)) this.substringAfter(delimiter) else null
fun String.substringBeforeOrNull(delimiter: Char): String? = if (this.contains(delimiter)) this.substringBefore(delimiter) else null
fun String.substringAfterLastOrNull(delimiter: Char): String? = if (this.contains(delimiter)) this.substringAfterLast(delimiter) else null
fun String.substringBeforeLastOrNull(delimiter: Char): String? = if (this.contains(delimiter)) this.substringBeforeLast(delimiter) else null

fun String.substringAfterOrNull(delimiter: String): String? = if (this.contains(delimiter)) this.substringAfter(delimiter) else null
fun String.substringBeforeOrNull(delimiter: String): String? = if (this.contains(delimiter)) this.substringBefore(delimiter) else null
fun String.substringAfterLastOrNull(delimiter: String): String? = if (this.contains(delimiter)) this.substringAfterLast(delimiter) else null
fun String.substringBeforeLastOrNull(delimiter: String): String? = if (this.contains(delimiter)) this.substringBeforeLast(delimiter) else null


inline fun String.toBase64(startIndex: Int = 0, endIndex: Int = this.length) = this.encodeToByteArray(startIndex, endIndex).toBase64()

inline fun CharSequence.decodeBase64(startIndex: Int = 0, endIndex: Int = this.length): String = this.decodeBase64ToByteArray(startIndex, endIndex).decodeToString()


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