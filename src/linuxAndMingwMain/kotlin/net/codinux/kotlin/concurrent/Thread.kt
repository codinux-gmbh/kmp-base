package net.codinux.kotlin.concurrent

import kotlin.native.concurrent.Worker

actual class Thread(private val worker: Worker) {

    actual companion object {

        actual val current: Thread
            get() = Thread(Worker.current)

    }


    actual constructor() : this(Worker.start())

    constructor(name: String) : this(Worker.start(name = name))


    actual val name: String
        get() = worker.name // TODO: use native C implementation

    val id: Long = worker.id.toLong()


    actual fun getStackTrace(): List<String> {
        return Exception().getStackTrace().asList()
            .drop(3) // remove invocation of Throwable constructor, Exception constructor and this method
    }

}