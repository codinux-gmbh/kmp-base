package net.codinux.kotlin.concurrent

import platform.Foundation.NSOperationQueue
import platform.Foundation.NSThread

actual class Thread(private val thread: NSThread) {

    actual companion object {

        actual val current: Thread
            get() = Thread(NSThread.currentThread)

        val mainThread: Thread = Thread(NSThread.mainThread)

    }


    actual constructor() : this(NSThread())

    constructor(name: String) : this(NSThread().apply { this.name = name })


    actual val name: String
        get() {

            return thread.name?.takeIf { it.isNotBlank() }
                ?: thread.description
                ?: NSOperationQueue.currentQueue?.underlyingQueue?.description
                ?: "Could not retrieve thread's name"
        }

    val isMainThread: Boolean = thread.isMainThread

    val isExecuting: Boolean
        get() = thread.isExecuting()

    val isFinished: Boolean
        get() = thread.isFinished()

    val isCancelled: Boolean
        get() = thread.isCancelled()

    fun setThreadLocal() {
//        thread.threadDictionary
    }

    fun cancel() {
        thread.cancel()
    }


    actual fun getStackTrace(): List<String> {
        return NSThread.callStackSymbols
            .drop(1) // skip invocation of Exception constructor and this method
            .map { it.toString() }
    }

}