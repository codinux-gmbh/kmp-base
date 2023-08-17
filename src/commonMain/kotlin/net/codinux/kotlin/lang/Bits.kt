package net.codinux.kotlin.lang

// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/kmem/src/commonMain/kotlin/korlibs/memory/Bits.kt

/** Takes n[bits] of [this] [Int], and extends the last bit, creating a plain [Int] in one's complement */
fun Int.signExtend(bits: Int): Int = (this shl (32 - bits)) shr (32 - bits) // Int.SIZE_BITS

/** Creates an [Int] with [this] bits set to 1 */
fun Int.mask(): Int = (1 shl this) - 1

/** Extracts [count] bits at [offset] from [this] [Int] */
fun Int.extract(offset: Int, count: Int): Int = (this ushr offset) and count.mask()

/** Extracts 8 bits at [offset] from [this] [Int] as [Byte] */
fun Int.extractByte(offset: Int): Byte = (this ushr offset).toByte()

/** Replaces [this] bits from [offset] to [offset]+[count] with [value] and returns the result of doing such replacement */
fun Int.insert(value: Int, offset: Int, count: Int): Int {
    val mask = count.mask()
    val clearValue = this and (mask shl offset).inv()
    return clearValue or ((value and mask) shl offset)
}