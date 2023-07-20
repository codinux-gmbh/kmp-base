package net.codinux.kotlin.concurrent

import kotlin.concurrent.AtomicReference

actual class AtomicReference<T> actual constructor(value: T?) {

    private val impl = AtomicReference(value)

    actual fun get(): T? = impl.value

    actual fun set(newValue: T?) {
        impl.value = newValue
    }

    actual fun getAndSet(newValue: T?) = impl.getAndSet(newValue)

    override fun toString() = get().toString()

}