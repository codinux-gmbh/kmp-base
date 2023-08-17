package net.codinux.kotlin.lang

// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/kmem/src/commonMain/kotlin/korlibs/memory/ByteArrayReadWrite.kt

private fun ByteArray.u8(o: Int): Int = this[o].toInt() and 0xFF

private inline fun ByteArray.read16LE(o: Int): Int = (u8(o + 0) shl 0) or (u8(o + 1) shl 8)

private inline fun ByteArray.read16BE(o: Int): Int = (u8(o + 1) shl 0) or (u8(o + 0) shl 8)

// Signed
fun ByteArray.readS16LE(o: Int): Int = read16LE(o).signExtend(16)
fun ByteArray.readS16BE(o: Int): Int = read16BE(o).signExtend(16)

// Custom Endian
fun ByteArray.readS16(o: Int, little: Boolean): Int = if (little) readS16LE(o) else readS16BE(o)

fun ByteArray.write16(o: Int, v: Int, little: Boolean) { if (little) write16LE(o, v) else write16BE(o, v) }
fun ByteArray.write16LE(o: Int, v: Int) { this[o + 0] = v.extractByte(0); this[o + 1] = v.extractByte(8) }
fun ByteArray.write16BE(o: Int, v: Int) { this[o + 1] = v.extractByte(0); this[o + 0] = v.extractByte(8) }