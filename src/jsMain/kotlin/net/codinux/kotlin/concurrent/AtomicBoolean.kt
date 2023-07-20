package net.codinux.kotlin.concurrent

actual class AtomicBoolean actual constructor(private var value: Boolean) {

    actual fun get() = this.value

    actual fun set(newValue: Boolean) {
        this.value = newValue
    }

    override fun toString() = value.toString()

}