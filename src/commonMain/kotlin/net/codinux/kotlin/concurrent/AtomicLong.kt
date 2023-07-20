package net.codinux.kotlin.concurrent

expect class AtomicLong(value: Long = 0) {

    fun get(): Long

    fun set(newValue: Long)

    fun incrementAndGet(): Long

    fun decrementAndGet(): Long

    fun addAndGet(delta: Long): Long

    fun getAndIncrement(): Long

    fun getAndDecrement(): Long

    fun getAndAdd(delta: Long): Long

}