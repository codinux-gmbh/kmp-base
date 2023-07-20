package net.codinux.kotlin.concurrent

actual class AtomicLong actual constructor(private var value: Long) {

    actual fun get() = this.value

    actual fun set(newValue: Long) {
        this.value = newValue
    }

    actual fun incrementAndGet(): Long {
        this.value += 1
        return get()
    }

    actual fun decrementAndGet(): Long {
        this.value -= 1
        return get()
    }

    actual fun addAndGet(delta: Long): Long {
        this.value += delta
        return get()
    }

    actual fun getAndIncrement(): Long {
        val oldValue = get()
        this.value += 1
        return oldValue
    }

    actual fun getAndDecrement(): Long {
        val oldValue = get()
        this.value -= 1
        return oldValue
    }

    actual fun getAndAdd(delta: Long): Long {
        val oldValue = get()
        this.value += delta
        return oldValue
    }

    override fun toString() = value.toString()

}