package net.codinux.kotlin.concurrent

expect class WeakReference<T : Any>(value: T) {

    fun get(): T?

}