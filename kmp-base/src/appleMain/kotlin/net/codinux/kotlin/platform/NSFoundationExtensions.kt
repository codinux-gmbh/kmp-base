@file:OptIn(UnsafeNumber::class, ExperimentalForeignApi::class, UnsafeNumber::class, BetaInteropApi::class)

package net.codinux.kotlin.platform

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.darwin.NSUInteger


fun Int.toNSNumber() = NSNumber.numberWithInt(this)
fun Long.toNSNumber() = NSNumber.numberWithLongLong(this)
fun Double.toNSNumber() = NSNumber.numberWithDouble(this)

fun Int.toNSUInteger(): NSUInteger = this.toNSNumber().unsignedIntegerValue
fun Long.toNSUInteger(): NSUInteger = this.toNSNumber().unsignedIntegerValue

fun <T> NSArray.toList(): List<T> {
    val result = mutableListOf<T>()

    for (i in 0L until this.count.toLong()) {
        @Suppress("UNCHECKED_CAST")
        result.add(this.objectAtIndex(i.toNSUInteger()) as T)
    }

    return result
}


fun NSDictionary.getString(key: String): String? {
    return this.objectForKey(key) as? String
}

fun NSDictionary.getString(key: String, defaultValue: String): String {
    return this.getString(key) ?: defaultValue
}

fun NSDictionary.getStringOrEmpty(key: String): String {
    return this.getString(key, "")
}


fun NSComparisonResult.toCompareToResult(): Int {
    return when (this) {
        NSOrderedSame -> 0
        NSOrderedAscending -> -1
        NSOrderedDescending -> 1
        else -> 0
    }
}


fun String.toNSString(): NSString {
    return NSString.create(string = this)
}

fun String.toNSData(): NSData {
    return this.encodeToByteArray().toNSData()
}

fun ByteArray.toNSData(): NSData = NSMutableData().apply {
    if (isEmpty()) return@apply
    this@toNSData.usePinned {
        appendBytes(it.addressOf(0), size.convert())
    }
}

fun NSData.toByteArray(): ByteArray {
    val data: CPointer<ByteVar> = bytes!!.reinterpret()

    return ByteArray(length.toInt()) { index -> data[index] }
}