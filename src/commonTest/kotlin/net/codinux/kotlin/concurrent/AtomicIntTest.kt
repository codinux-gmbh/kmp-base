package net.codinux.kotlin.concurrent

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test

class AtomicIntTest {

    @Test
    fun changeValue() = runTest {
        val underTest = AtomicInt(7)

        withContext(Dispatchers.Default) {
            underTest.set(42)
        }

        underTest.get().shouldBe(42)
    }

    @Test
    fun incrementAndGet() = runTest {
        val initialValue = 41

        val underTest = AtomicInt(initialValue)

        withContext(Dispatchers.Default) {
            underTest.incrementAndGet().shouldBe(initialValue + 1)
        }

        underTest.get().shouldBe(initialValue + 1)
    }

}