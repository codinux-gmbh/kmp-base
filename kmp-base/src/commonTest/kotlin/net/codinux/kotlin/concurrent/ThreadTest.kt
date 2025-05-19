package net.codinux.kotlin.concurrent

import assertk.assertThat
import assertk.assertions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import net.codinux.kotlin.platform.Platform
import net.codinux.kotlin.platform.isJavaScript
import kotlin.test.Test

class ThreadTest {

    @Test
    fun nameIsSet() {
        val underTest = Thread.current

        assertThat(underTest.name).isNotEmpty()
    }

    @Test
    fun threadNamesDiffer() = runTest {
        val thread1Name = Thread.current.name

        val thread2Name = withContext(Dispatchers.Default) {
            Thread.current.name
        }

        if (Platform.isJavaScript == false) { // JavaScript has no notion of threads
            assertThat(thread1Name).isNotEqualTo(thread2Name)
        }
    }

    @Test
    fun startNewThread() = runTest {
        val thread1Name = Thread.current.name

        val thread2Name = Thread().name

        if (Platform.isJavaScript == false) { // JavaScript has no notion of threads
            assertThat(thread1Name).isNotEqualTo(thread2Name)
        }
    }

    @Test
    fun getStackTrace() {
        val result = Thread.current.getStackTrace()

        assertThat(result.size).isGreaterThanOrEqualTo(4)

        if (Platform.isJavaScript) { // different browser implementations have different stack traces, so it's impossible to identify the universally correct number of stack trace elements to remove
            assertThat(result.any { it.contains("ThreadTest") }).isTrue()
        } else {
            assertThat(result.first()).contains("ThreadTest")
        }
    }

}