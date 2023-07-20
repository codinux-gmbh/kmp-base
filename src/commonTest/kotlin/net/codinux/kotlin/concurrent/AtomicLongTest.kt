package net.codinux.kotlin.concurrent

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test

class AtomicLongTest {

    @Test
    fun changeValue() = runTest {
        val underTest = AtomicLong(7)

        withContext(Dispatchers.Default) {
            underTest.set(42)
        }

        underTest.get().shouldBe(42)
    }

    @Test
    fun incrementAndGet() = runTest {
        val initialValue = 41L

        val underTest = AtomicLong(initialValue)

        withContext(Dispatchers.Default) {
            underTest.incrementAndGet().shouldBe(initialValue + 1)
        }

        underTest.get().shouldBe(initialValue + 1)
    }

    @Test
    fun decrementAndGet() = runTest {
        val initialValue = 43L

        val underTest = AtomicLong(initialValue)

        withContext(Dispatchers.Default) {
            underTest.decrementAndGet().shouldBe(initialValue - 1)
        }

        underTest.get().shouldBe(initialValue - 1)
    }

    @Test
    fun addAndGet() = runTest {
        val initialValue = 1L
        val delta = 41L

        val underTest = AtomicLong(initialValue)

        withContext(Dispatchers.Default) {
            underTest.addAndGet(delta).shouldBe(initialValue + delta)
        }

        underTest.get().shouldBe(initialValue + delta)
    }

    @Test
    fun getAndIncrement() = runTest {
        val initialValue = 41L

        val underTest = AtomicLong(initialValue)

        withContext(Dispatchers.Default) {
            underTest.getAndIncrement().shouldBe(initialValue)
        }

        underTest.get().shouldBe(initialValue + 1)
    }

    @Test
    fun getAndDecrement() = runTest {
        val initialValue = 43L

        val underTest = AtomicLong(initialValue)

        withContext(Dispatchers.Default) {
            underTest.getAndDecrement().shouldBe(initialValue)
        }

        underTest.get().shouldBe(initialValue - 1)
    }

    @Test
    fun getAndAdd() = runTest {
        val initialValue = 1L
        val delta = 41L

        val underTest = AtomicLong(initialValue)

        withContext(Dispatchers.Default) {
            underTest.getAndAdd(delta).shouldBe(initialValue)
        }

        underTest.get().shouldBe(initialValue + delta)
    }

}