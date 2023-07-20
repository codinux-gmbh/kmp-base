package net.codinux.kotlin.concurrent

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test

class WeakReferenceTest {

    @Test
    fun changeValue() = runTest {
        val underTest = WeakReference(42)

        withContext(Dispatchers.Default) {
            underTest.get().shouldBe(42)
        }
    }

}