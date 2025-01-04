package net.codinux.kotlin.extensions

import kotlinx.cinterop.*

@OptIn(ExperimentalForeignApi::class)
fun CPointer<ByteVar>?.toChar() = this?.pointed?.value?.toInt()?.toChar()