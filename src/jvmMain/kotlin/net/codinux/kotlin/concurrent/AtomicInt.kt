package net.codinux.kotlin.concurrent

import java.util.concurrent.atomic.AtomicInteger

actual class AtomicInt actual constructor(value: Int) {

    private val impl = AtomicInteger(value)

    actual fun get() = impl.get()

    actual fun set(newValue: Int) = impl.set(newValue)

    actual fun incrementAndGet() = impl.incrementAndGet()

    override fun toString() = impl.toString()

}