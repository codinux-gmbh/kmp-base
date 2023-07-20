package net.codinux.kotlin.concurrent

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test

class AtomicReferenceTest {

    @Test
    fun changeValueAsynchronously() = runTest {
        val underTest = AtomicReference("Old")

        withContext(Dispatchers.Default) {
            underTest.set("New")
        }

        underTest.get().shouldBe("New")
    }

    @Test
    fun getAndSet() = runTest {
        val underTest = AtomicReference("Old")

        withContext(Dispatchers.Default) {
            underTest.getAndSet("New").shouldBe("Old")
        }

        underTest.get().shouldBe("New")
    }

}