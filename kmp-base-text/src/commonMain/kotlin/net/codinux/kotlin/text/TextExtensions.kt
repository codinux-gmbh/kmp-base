package net.codinux.kotlin.text


// i used 'internal' for all methods to not pollute global namespace


internal fun StringBuilder.appendCodePointV(codePoint: Int) {
    if (codePoint in 0xD800..0xDFFF || codePoint > 0xFFFF) {
        val U0 = codePoint - 0x10000
        val hs = U0.extract(10, 10)
        val ls = U0.extract(0, 10)
        append(((0b110110 shl 10) or (hs)).toChar())
        append(((0b110111 shl 10) or (ls)).toChar())
    } else {
        append(codePoint.toChar())
    }
}


// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/kmem/src/commonMain/kotlin/korlibs/memory/Bits.kt

/** Takes n[bits] of [this] [Int], and extends the last bit, creating a plain [Int] in one's complement */
internal fun Int.signExtend(bits: Int): Int = (this shl (32 - bits)) shr (32 - bits) // Int.SIZE_BITS

/** Creates an [Int] with [this] bits set to 1 */
internal fun Int.mask(): Int = (1 shl this) - 1

/** Extracts [count] bits at [offset] from [this] [Int] */
internal fun Int.extract(offset: Int, count: Int): Int = (this ushr offset) and count.mask()

/** Extracts 8 bits at [offset] from [this] [Int] as [Byte] */
internal fun Int.extractByte(offset: Int): Byte = (this ushr offset).toByte()

/** Replaces [this] bits from [offset] to [offset]+[count] with [value] and returns the result of doing such replacement */
internal fun Int.insert(value: Int, offset: Int, count: Int): Int {
    val mask = count.mask()
    val clearValue = this and (mask shl offset).inv()
    return clearValue or ((value and mask) shl offset)
}


// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/kmem/src/commonMain/kotlin/korlibs/memory/ByteArrayReadWrite.kt

private fun ByteArray.u8(o: Int): Int = this[o].toInt() and 0xFF

private inline fun ByteArray.read16LE(o: Int): Int = (u8(o + 0) shl 0) or (u8(o + 1) shl 8)

private inline fun ByteArray.read16BE(o: Int): Int = (u8(o + 1) shl 0) or (u8(o + 0) shl 8)

// Signed
internal fun ByteArray.readS16LE(o: Int): Int = read16LE(o).signExtend(16)
internal fun ByteArray.readS16BE(o: Int): Int = read16BE(o).signExtend(16)

// Custom Endian
internal fun ByteArray.readS16(o: Int, little: Boolean): Int = if (little) readS16LE(o) else readS16BE(o)

internal fun ByteArray.write16(o: Int, v: Int, little: Boolean) { if (little) write16LE(o, v) else write16BE(o, v) }
internal fun ByteArray.write16LE(o: Int, v: Int) { this[o + 0] = v.extractByte(0); this[o + 1] = v.extractByte(8) }
internal fun ByteArray.write16BE(o: Int, v: Int) { this[o + 1] = v.extractByte(0); this[o + 0] = v.extractByte(8) }