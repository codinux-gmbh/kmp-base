package net.codinux.kotlin.concurrent

expect class AtomicInt(value: Int = 0) {

    fun get(): Int

    fun set(newValue: Int)

    fun incrementAndGet(): Int

}