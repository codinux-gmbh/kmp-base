package net.codinux.kotlin.concurrent

actual class Thread actual constructor() {

    actual companion object {

        actual val current: Thread
            get() = Thread()

    }

    actual val name = "main"

    actual fun getStackTrace(): List<String> {
        return Exception().stackTraceToString().split('\n')
            .drop(2) // skip invocation of Exception constructor and this method
    }

}