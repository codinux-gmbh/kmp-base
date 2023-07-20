package net.codinux.kotlin.concurrent

import kotlin.concurrent.AtomicLong

actual class AtomicLong actual constructor(value: Long) {

    private val impl = AtomicLong(value)

    actual fun get() = impl.value

    actual fun set(newValue: Long) {
        impl.value = newValue
    }

    actual fun incrementAndGet() = impl.incrementAndGet()

    actual fun decrementAndGet() = impl.decrementAndGet()

    actual fun addAndGet(delta: Long) = impl.addAndGet(delta)

    actual fun getAndIncrement() = impl.getAndIncrement()

    actual fun getAndDecrement() = impl.getAndDecrement()

    actual fun getAndAdd(delta: Long) = impl.getAndAdd(delta)

    override fun toString() = get().toString()

}