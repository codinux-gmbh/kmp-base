package net.codinux.kotlin.concurrent

import java.util.concurrent.atomic.AtomicReference

actual class AtomicReference<T> actual constructor(value: T?) {

    private val impl = AtomicReference(value)

    actual fun get(): T? = impl.get()

    actual fun set(newValue: T?) = impl.set(newValue)

    actual fun getAndSet(newValue: T?) = impl.getAndSet(newValue)

    override fun toString() = impl.toString()

}