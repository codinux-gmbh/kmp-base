package net.codinux.kotlin.concurrent

actual class AtomicInt actual constructor(private var value: Int) {

    actual fun get() = this.value

    actual fun set(newValue: Int) {
        this.value = newValue
    }

    actual fun incrementAndGet(): Int {
        this.value += 1
        return this.value
    }

    override fun toString() = value.toString()

}