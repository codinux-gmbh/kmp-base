package net.codinux.kotlin.concurrent

import java.util.concurrent.atomic.AtomicLong

actual class AtomicLong actual constructor(value: Long) {

    private val impl = AtomicLong(value)

    actual fun get() = impl.get()

    actual fun set(newValue: Long) = impl.set(newValue)

    actual fun incrementAndGet() = impl.incrementAndGet()

    actual fun decrementAndGet() = impl.decrementAndGet()

    actual fun addAndGet(delta: Long) = impl.addAndGet(delta)

    actual fun getAndIncrement() = impl.getAndIncrement()

    actual fun getAndDecrement() = impl.getAndDecrement()

    actual fun getAndAdd(delta: Long) = impl.getAndAdd(delta)

    override fun toString() = impl.toString()

}