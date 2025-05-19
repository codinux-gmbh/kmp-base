package net.codinux.kotlin.concurrent

import net.codinux.kotlin.platform.Platform
import net.codinux.kotlin.platform.isBrowser

actual class Thread actual constructor() {

    actual companion object {

        actual val current: Thread
            get() = Thread()

        // different browser implementations have different stack traces, so it's impossible to determine the universally correct number of stack trace elements to remove
        private val countStackTraceElementsToSkip by lazy { if (Platform.isBrowser) 3 else 2 }

    }

    actual val name = "main"

    /**
     * Be aware for JS Browser due to the different Browser implementations it's not possible to determine the
     * universally correct number of stack trace elements to remove to get only the calling method's stack trace.
     */
    actual fun getStackTrace(): List<String> {
        return Exception().stackTraceToString().split('\n')
            .drop(countStackTraceElementsToSkip) // skip invocation of Exception constructor and this method
    }

}