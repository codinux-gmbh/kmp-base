package net.codinux.kotlin.concurrent

actual class WeakReference<T : Any> actual constructor(value: T) {

    private val impl = kotlin.native.ref.WeakReference(value)

    actual fun get() = impl.get()

    override fun toString() = get().toString()

}