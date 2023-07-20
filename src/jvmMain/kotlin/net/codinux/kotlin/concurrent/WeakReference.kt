package net.codinux.kotlin.concurrent

import java.lang.ref.WeakReference

actual class WeakReference<T : Any> actual constructor(value: T) {

    private val impl = WeakReference(value)

    actual fun get() = impl.get()

    override fun toString() = impl.toString()

}