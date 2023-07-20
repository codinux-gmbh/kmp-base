package net.codinux.kotlin.concurrent

actual class Thread(private val thread: java.lang.Thread) {

    actual companion object {

        actual val current: Thread
            get() = Thread(java.lang.Thread.currentThread())

    }


    actual constructor() : this(java.lang.Thread())

    constructor(name: String): this(java.lang.Thread(name))


    actual val name: String
        get() = thread.name

    val id: Long = thread.id


    actual fun getStackTrace(): List<String> {
        return thread.stackTrace
            .drop(2) // skip invocation of java.lang.Thread.getStackTrace() and this method
            .map { it.toString() }
    }

}