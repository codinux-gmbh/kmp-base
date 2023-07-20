package net.codinux.kotlin.collections

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class ImmutableListTest {

    companion object {
        private const val CountElements = 12

        private val TestData = mutableListOf<String>().apply {
            for (i in 0..< CountElements) {
                add(i.toString())
            }
        }
    }


    @Test
    fun getSize() {
        val underTest = ImmutableList(TestData)

        underTest.shouldHaveSize(CountElements)
    }

    @Test
    fun isEmpty() {
        val underTest = ImmutableList(emptyList<String>())

        underTest.isEmpty().shouldBeTrue()
    }

    @Test
    fun isNotEmpty() {
        val underTest = ImmutableList(TestData)

        underTest.isNotEmpty().shouldBeTrue()
    }

    @Test
    fun iterationOrderRemains() {
        val underTest = ImmutableList(TestData)

        for (i in 0..< CountElements) {
            TestData[i].shouldBe(underTest[i])
        }
    }

    @Test
    fun indexOf() {
        val underTest = ImmutableList(TestData)

        for (index in 0..< CountElements) {
            underTest.indexOf(TestData[index]).shouldBe(index)
        }
    }

    @Test
    fun contains() {
        val underTest = ImmutableList(TestData)

        for (index in 0..< CountElements) {
            underTest.contains(TestData[index]).shouldBeTrue()
        }
    }

    @Test
    fun containsAll() {
        val underTest = ImmutableList(TestData)

        underTest.containsAll(TestData).shouldBeTrue()
    }
}