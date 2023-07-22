package net.codinux.kotlin.concurrent

actual class AtomicLong actual constructor(private var value: Long) {

    actual fun get() = this.value

    actual fun set(newValue: Long) {
        this.value = newValue
    }

    actual fun incrementAndGet() = addAndGet(1)

    actual fun decrementAndGet() = addAndGet(-1)

    actual fun addAndGet(delta: Long): Long {
        set(get() + delta)
        return get()
    }

    actual fun getAndIncrement() = getAndAdd(1)

    actual fun getAndDecrement() = getAndAdd(-1)

    actual fun getAndAdd(delta: Long): Long {
        val oldValue = get()
        set(oldValue + delta)
        return oldValue
    }

    override fun toString() = value.toString()

}