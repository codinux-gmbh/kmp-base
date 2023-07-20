package net.codinux.kotlin.collections

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class ImmutableCollectionTest {

    companion object {
        private const val CountElements = 12

        private val TestData = mutableSetOf<String>().apply {
            for (i in 0..< CountElements) {
                add(i.toString())
            }
        }
    }


    @Test
    fun getSize() {
        val underTest = ImmutableCollection(TestData)

        underTest.shouldHaveSize(CountElements)
    }

    @Test
    fun isEmpty() {
        val underTest = ImmutableCollection(emptySet<String>())

        underTest.isEmpty().shouldBeTrue()
    }

    @Test
    fun isNotEmpty() {
        val underTest = ImmutableCollection(TestData)

        underTest.isNotEmpty().shouldBeTrue()
    }

    @Test
    fun indexOf() {
        val underTest = ImmutableCollection(TestData)

        for (index in 0..< CountElements) {
            underTest.indexOf(index.toString()).shouldBe(index)
        }
    }

    @Test
    fun contains() {
        val underTest = ImmutableCollection(TestData)

        for (index in 0..< CountElements) {
            underTest.contains(index.toString()).shouldBeTrue()
        }
    }

    @Test
    fun containsAll() {
        val underTest = ImmutableCollection(TestData)

        underTest.containsAll(TestData).shouldBeTrue()
    }
}