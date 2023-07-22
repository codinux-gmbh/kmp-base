package net.codinux.kotlin.concurrent

import kotlin.native.concurrent.AtomicLong

actual class AtomicLong actual constructor(value: Long) {

    private val impl = AtomicLong(value)

    actual fun get() = impl.value

    actual fun set(newValue: Long) {
        impl.value = newValue
    }

    actual fun incrementAndGet() = addAndGet(1) // TODO: after upgrading to Kotlin 1.9 use impl.incrementAndGet()

    actual fun decrementAndGet() = addAndGet(-1) // TODO: after upgrading to Kotlin 1.9 use impl.decrementAndGet()

    actual fun addAndGet(delta: Long) = impl.addAndGet(delta)

    actual fun getAndIncrement() = getAndAdd(1) // TODO: after upgrading to Kotlin 1.9 use impl.getAndIncrement()

    actual fun getAndDecrement() = getAndAdd(-1) // TODO: after upgrading to Kotlin 1.9 use impl.getAndDecrement()

    // TODO: after upgrading to Kotlin 1.9 use impl.getAndAdd(delta)
    actual fun getAndAdd(delta: Long): Long {
        var oldValue = get()

        while (impl.compareAndSet(oldValue, oldValue + delta) == false) {
            oldValue = get()
        }

        return oldValue
    }

    override fun toString() = get().toString()

}