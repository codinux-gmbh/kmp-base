package net.codinux.kotlin.concurrent

import kotlin.native.concurrent.AtomicInt

actual class AtomicInt actual constructor(value: Int) {

    private val impl = AtomicInt(value)

    actual fun get() = impl.value

    actual fun set(newValue: Int) {
        impl.value = newValue
    }

    actual fun incrementAndGet() = addAndGet(1) // TODO: after upgrading to Kotlin 1.9 use impl.incrementAndGet()

    actual fun decrementAndGet() = addAndGet(-1) // TODO: after upgrading to Kotlin 1.9 use impl.decrementAndGet()

    actual fun addAndGet(delta: Int) = impl.addAndGet(delta)

    actual fun getAndIncrement() = getAndAdd(1) // TODO: after upgrading to Kotlin 1.9 use impl.getAndIncrement()

    actual fun getAndDecrement() = getAndAdd(-1) // TODO: after upgrading to Kotlin 1.9 use impl.getAndDecrement()

    // TODO: after upgrading to Kotlin 1.9 use impl.getAndAdd(delta)
    actual fun getAndAdd(delta: Int): Int {
        var oldValue = get()

        while (impl.compareAndSet(oldValue, oldValue + delta) == false) {
            oldValue = get()
        }

        return oldValue
    }

    override fun toString() = get().toString()

}