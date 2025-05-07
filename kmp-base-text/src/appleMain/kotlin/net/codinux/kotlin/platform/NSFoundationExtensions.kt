@file:OptIn(UnsafeNumber::class, ExperimentalForeignApi::class, BetaInteropApi::class)

package net.codinux.kotlin.platform

import kotlinx.cinterop.*
import platform.Foundation.*


/*      I re-added these methods here so that we don't need a dependency on kmp-base        */

internal fun String.toNSString(): NSString {
    return NSString.create(string = this)
}

internal fun String.toNSData(): NSData {
    return this.encodeToByteArray().toNSData()
}

internal fun ByteArray.toNSData(): NSData = NSMutableData().apply {
    if (isEmpty()) return@apply
    this@toNSData.usePinned {
        appendBytes(it.addressOf(0), size.convert())
    }
}

internal fun NSData.toByteArray(): ByteArray {
    val data: CPointer<ByteVar> = bytes!!.reinterpret()

    return ByteArray(length.toInt()) { index -> data[index] }
}