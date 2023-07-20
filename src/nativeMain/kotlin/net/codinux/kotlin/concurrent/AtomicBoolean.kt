package net.codinux.kotlin.concurrent

import kotlin.concurrent.AtomicReference

actual class AtomicBoolean actual constructor(value: Boolean) {

    private val impl = AtomicReference(value)

    actual fun get() = impl.value

    actual fun set(newValue: Boolean) {
        impl.value = newValue
    }

    override fun toString() = get().toString()

}