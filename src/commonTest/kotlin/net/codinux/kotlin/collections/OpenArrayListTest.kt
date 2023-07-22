package net.codinux.kotlin.collections

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import net.codinux.kotlin.collections.CollectionsTestData.CountElements
import net.codinux.kotlin.collections.CollectionsTestData.ListTestData
import kotlin.test.Test

class OpenArrayListTest {

    @Test
    fun getSize() {
        val underTest = OpenArrayList(ListTestData)

        underTest.shouldHaveSize(CountElements)
    }

    @Test
    fun isEmpty() {
        val underTest = OpenArrayList(emptyList<String>())

        underTest.isEmpty().shouldBeTrue()
    }

    @Test
    fun isNotEmpty() {
        val underTest = OpenArrayList(ListTestData)

        underTest.isNotEmpty().shouldBeTrue()
    }

    @Test
    fun iterationOrderRemains() {
        val underTest = OpenArrayList(ListTestData)

        CollectionsTestData.forAllElements { index ->
            ListTestData[index].shouldBe(underTest[index])
        }
    }

    @Test
    fun indexOf() {
        val underTest = OpenArrayList(ListTestData)

        CollectionsTestData.forAllElements { index ->
            underTest.indexOf(ListTestData[index]).shouldBe(index)
        }
    }

    @Test
    fun contains() {
        val underTest = OpenArrayList(ListTestData)

        CollectionsTestData.forAllElements { index ->
            underTest.contains(ListTestData[index]).shouldBeTrue()
        }
    }

    @Test
    fun containsAll() {
        val underTest = OpenArrayList(ListTestData)

        underTest.containsAll(ListTestData).shouldBeTrue()
    }
}