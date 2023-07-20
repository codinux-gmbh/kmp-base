package net.codinux.kotlin.concurrent

expect class AtomicReference<T>(value: T? = null) {

    fun get(): T?

    fun set(newValue: T?)

    fun getAndSet(newValue: T?): T?

}