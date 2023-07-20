package net.codinux.kotlin.concurrent

import java.util.concurrent.atomic.AtomicBoolean

actual class AtomicBoolean actual constructor(value: Boolean) {

    private val impl = AtomicBoolean(value)

    actual fun get() = impl.get()

    actual fun set(newValue: Boolean) = impl.set(newValue)

    override fun toString() = impl.toString()

}