package net.codinux.kotlin.concurrent

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test

class AtomicBooleanTest {

    @Test
    fun fromFalseToTrue() = runTest {
        val underTest = AtomicBoolean(false)

        withContext(Dispatchers.Default) {
            underTest.set(true)
        }

        underTest.get().shouldBeTrue()
    }

    @Test
    fun fromTrueToFalse() = runTest {
        val underTest = AtomicBoolean(true)

        withContext(Dispatchers.Default) {
            underTest.set(false)
        }

        underTest.get().shouldBeFalse()
    }

}