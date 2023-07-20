package net.codinux.kotlin.concurrent

actual class AtomicReference<T> actual constructor(private var value: T?) {

    actual fun get() = this.value

    actual fun set(newValue: T?) {
        this.value = newValue
    }

    actual fun getAndSet(newValue: T?): T? {
        val oldValue = get()

        set(newValue)

        return oldValue
    }

    override fun toString() = value.toString()

}