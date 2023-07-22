package net.codinux.kotlin.concurrent

import kotlin.native.concurrent.AtomicReference

actual class AtomicReference<T> actual constructor(value: T?) {

    private val impl = AtomicReference(value)

    actual fun get(): T? = impl.value

    actual fun set(newValue: T?) {
        impl.value = newValue
    }

    // TODO: after upgrading to Kotlin 1.9 use impl.getAndSet(newValue)
    actual fun getAndSet(newValue: T?): T? {
        var oldValue = get()

        while (impl.compareAndSet(oldValue, newValue) == false) {
            oldValue = get()
        }

        return oldValue
    }

    override fun toString() = get().toString()

}