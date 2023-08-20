package net.codinux.kotlin.platform.posix

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.value

fun CPointer<ByteVar>?.toChar() = this?.pointed?.value?.toInt()?.toChar()