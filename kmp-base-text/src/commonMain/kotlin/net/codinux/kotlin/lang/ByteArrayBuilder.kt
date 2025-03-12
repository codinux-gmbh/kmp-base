package net.codinux.kotlin.lang

import kotlin.math.max

// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/kmem/src/commonMain/kotlin/korlibs/memory/ByteArrayBuilder.kt

/**
 * Analogous to [StringBuilder] but for [ByteArray]. Allows to [append] values to end calling [toByteArray].
 * Provides some methods like [s16LE] or [f32BE] to append specific bit representations easily.
 */
open class ByteArrayBuilder(var data: ByteArray, size: Int = data.size, val allowGrow: Boolean = true) {

    constructor(initialCapacity: Int = 4096) : this(ByteArray(initialCapacity), 0)

    protected var _size: Int = size

    open var size: Int get() = _size
        set(value) {
            val oldPosition = _size
            val newPosition = value
            ensure(newPosition)
            _size = newPosition
            if (newPosition > oldPosition) {
                data.fill(0, oldPosition, newPosition)
            }
        }

    protected open fun ensure(expected: Int) {
        if (data.size < expected) {
            if (!allowGrow) throw RuntimeException("ByteArrayBuffer configured to not grow!")
            //val oldCapacity = data.size
            val newSize = (data.size + 7) * 5
            val realNewSize = if (newSize < 0) Int.MAX_VALUE / 2 else newSize
            if (newSize < 0 && expected > realNewSize) error("ByteArrayBuffer can't grow that much")
            data = data.copyOf(max(expected, realNewSize))
            //val newCapacity = data.size
            //println("GROW: $oldCapacity -> $newCapacity")
        }
    }

    protected open fun ensureCount(count: Int) {
        ensure(_size + count)
    }

    protected inline fun <T> prepare(count: Int, callback: () -> T): T {
        ensureCount(count)
        return callback().also { _size += count }
    }

    open fun append(array: ByteArray, offset: Int = 0, len: Int = array.size - offset) {
        ensureCount(len)
        array.copyInto(this.data, _size, offset, offset + len)
        this._size += len
    }

    // @TODO: This exists to not return a reference value, that has a performance hit on K/N, we should then ensure
    open fun appendFast(v: Byte) {
        ensure(_size + 1)
        data[_size++] = v
    }

    inline fun append(v: Byte): ByteArrayBuilder {
        appendFast(v)
        return this
    }

    open fun append(vararg v: Byte): Unit = append(v)

    open fun append(vararg v: Int): ByteArrayBuilder = this.apply {
        prepare(v.size) {
            for (n in 0 until v.size) this.data[this._size + n] = v[n].toByte()
        }
    }

    open fun appendByte(v: Int): ByteArrayBuilder = this.apply { prepare(1) { data[_size] = v.toByte() } }

    open fun clear() {
        _size = 0
    }

    open fun toByteArray(): ByteArray = data.copyOf(_size)

}