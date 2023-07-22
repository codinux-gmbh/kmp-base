package net.codinux.kotlin.concurrent

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotBeBlank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import net.codinux.kotlin.Platform
import net.codinux.kotlin.PlatformType
import kotlin.test.Test

class ThreadTest {

    @Test
    fun nameIsSet() {
        val underTest = Thread.current

        underTest.name.shouldNotBeBlank()
    }

    @Test
    fun threadNamesDiffer() = runTest {
        val thread1Name = Thread.current.name

        val thread2Name = withContext(Dispatchers.Default) {
            Thread.current.name
        }

        if (Platform.type.isJavaScript == false) { // JavaScript has no notion of threads
            thread1Name.shouldNotBe(thread2Name)
        }
    }

    @Test
    fun startNewThread() = runTest {
        val thread1Name = Thread.current.name

        val thread2Name = Thread().name

        if (Platform.type.isJavaScript == false) { // JavaScript has no notion of threads
            thread1Name.shouldNotBe(thread2Name)
        }
    }

    @Test
    fun getStackTrace() {
        val result = Thread.current.getStackTrace()

        result.shouldHaveAtLeastSize(4)

        if (Platform.type == PlatformType.JavaScriptBrowser) { // different browser implementations have different stack traces, so it's impossible to identify the universally correct number of stack trace elements to remove
            result.any { it.contains("ThreadTest") }.shouldBeTrue()
        } else {
            result.first().shouldContain("ThreadTest")
        }
    }

}