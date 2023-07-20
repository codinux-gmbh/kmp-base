package net.codinux.kotlin.collections

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class OpenArrayListTest {

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
        val underTest = OpenArrayList(TestData)

        underTest.shouldHaveSize(CountElements)
    }

    @Test
    fun isEmpty() {
        val underTest = OpenArrayList(emptyList<String>())

        underTest.isEmpty().shouldBeTrue()
    }

    @Test
    fun isNotEmpty() {
        val underTest = OpenArrayList(TestData)

        underTest.isNotEmpty().shouldBeTrue()
    }

    @Test
    fun iterationOrderRemains() {
        val underTest = OpenArrayList(TestData)

        for (i in 0..< CountElements) {
            TestData[i].shouldBe(underTest[i])
        }
    }

    @Test
    fun indexOf() {
        val underTest = OpenArrayList(TestData)

        for (index in 0..< CountElements) {
            underTest.indexOf(TestData[index]).shouldBe(index)
        }
    }

    @Test
    fun contains() {
        val underTest = OpenArrayList(TestData)

        for (index in 0..< CountElements) {
            underTest.contains(TestData[index]).shouldBeTrue()
        }
    }

    @Test
    fun containsAll() {
        val underTest = OpenArrayList(TestData)

        underTest.containsAll(TestData).shouldBeTrue()
    }
}