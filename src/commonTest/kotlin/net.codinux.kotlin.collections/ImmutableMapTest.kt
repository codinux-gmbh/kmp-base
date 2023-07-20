package net.codinux.kotlin.collections

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class ImmutableMapTest {

    companion object {
        private const val CountElements = 12

        private val TestData = mutableMapOf<Int, String>().apply {
            for (i in 0..< CountElements) {
                put(i, i.toString())
            }
        }
    }


    @Test
    fun getSize() {
        val underTest = ImmutableMap(TestData)

        underTest.shouldHaveSize(CountElements)
    }

    @Test
    fun isEmpty() {
        val underTest = ImmutableMap(emptyMap<String, String>())

        underTest.isEmpty().shouldBeTrue()
    }

    @Test
    fun isNotEmpty() {
        val underTest = ImmutableMap(TestData)

        underTest.isNotEmpty().shouldBeTrue()
    }

    @Test
    fun keys() {
        val underTest = ImmutableMap(TestData)

        underTest.keys.shouldContainAll(0, 1 ,2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    }

    @Test
    fun values() {
        val underTest = ImmutableMap(TestData)

        underTest.values.shouldContainAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
    }

    @Test
    fun get() {
        val underTest = ImmutableMap(TestData)

        for (index in 0..< CountElements) {
            underTest.get(index).shouldBe(index.toString())
        }
    }

    @Test
    fun containsKey() {
        val underTest = ImmutableMap(TestData)

        for (index in 0..< CountElements) {
            underTest.containsKey(index).shouldBeTrue()
        }
    }

    @Test
    fun containsValue() {
        val underTest = ImmutableMap(TestData)

        for (index in 0..< CountElements) {
            underTest.containsValue(index.toString()).shouldBeTrue()
        }
    }
}