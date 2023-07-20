package net.codinux.kotlin.concurrent

expect class Thread() {

    companion object {

        val current: Thread

    }


    val name: String

    fun getStackTrace(): List<String>

}