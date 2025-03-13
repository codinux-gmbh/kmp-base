package net.codinux.kotlin.lang

import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.khronos.webgl.set


fun ByteArray.toUint8Array(): Uint8Array {
    val uint8Array = Uint8Array(this.size)
    this.indices.forEach { i ->
        uint8Array[i] = this[i]
    }

    return uint8Array
}

fun Uint8Array.toByteArray(): ByteArray {
    val byteArray = ByteArray(this.length)
    byteArray.indices.forEach { i ->
        byteArray[i] = this[i]
    }

    return byteArray
}