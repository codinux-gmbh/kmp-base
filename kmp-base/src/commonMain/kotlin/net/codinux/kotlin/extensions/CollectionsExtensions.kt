package net.codinux.kotlin.extensions

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun <E> Collection<E>.containsNot(element: E) = this.contains(element) == false


@OptIn(ExperimentalEncodingApi::class)
inline fun ByteArray.toBase64(startIndex: Int = 0, endIndex: Int = this.size) = Base64.encode(this, startIndex, endIndex)

@OptIn(ExperimentalEncodingApi::class)
inline fun CharSequence.decodeBase64ToByteArray(startIndex: Int = 0, endIndex: Int = this.length) = Base64.decode(this, startIndex, endIndex)


inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun <T> Array<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun IntArray.fastForEach(callback: (Int) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun FloatArray.fastForEach(callback: (Float) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}

inline fun DoubleArray.fastForEach(callback: (Double) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}