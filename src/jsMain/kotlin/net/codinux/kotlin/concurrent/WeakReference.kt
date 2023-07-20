package net.codinux.kotlin.concurrent

actual class WeakReference<T : Any> actual constructor(private var value: T) {

    actual fun get(): T? = this.value

    override fun toString() = value.toString()

}