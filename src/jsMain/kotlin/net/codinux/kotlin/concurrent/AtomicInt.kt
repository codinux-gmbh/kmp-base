package net.codinux.kotlin.concurrent

actual class AtomicInt actual constructor(private var value: Int) {

    actual fun get() = this.value

    actual fun set(newValue: Int) {
        this.value = newValue
    }

    actual fun incrementAndGet() = addAndGet(1)

    actual fun decrementAndGet() = addAndGet(-1)

    actual fun addAndGet(delta: Int): Int {
        this.value += delta
        return get()
    }

    actual fun getAndIncrement() = getAndAdd(1)

    actual fun getAndDecrement() = getAndAdd(-1)

    actual fun getAndAdd(delta: Int): Int {
        val oldValue = get()
        this.value += delta
        return oldValue
    }

    override fun toString() = value.toString()

}