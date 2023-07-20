package net.codinux.kotlin.concurrent

expect class AtomicInt(value: Int = 0) {

    fun get(): Int

    fun set(newValue: Int)

    fun incrementAndGet(): Int

    fun decrementAndGet(): Int

    fun addAndGet(delta: Int): Int

    fun getAndIncrement(): Int

    fun getAndDecrement(): Int

    fun getAndAdd(delta: Int): Int

}