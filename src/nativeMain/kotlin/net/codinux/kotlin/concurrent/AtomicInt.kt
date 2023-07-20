package net.codinux.kotlin.concurrent

import kotlin.concurrent.AtomicInt

actual class AtomicInt actual constructor(value: Int) {

    private val impl = AtomicInt(value)

    actual fun get() = impl.value

    actual fun set(newValue: Int) {
        impl.value = newValue
    }

    actual fun incrementAndGet() = impl.addAndGet(1)

    override fun toString() = get().toString()

}