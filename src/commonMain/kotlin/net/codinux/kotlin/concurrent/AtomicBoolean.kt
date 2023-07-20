package net.codinux.kotlin.concurrent

expect class AtomicBoolean(value: Boolean = false) {

    fun get(): Boolean

    fun set(newValue: Boolean)

}