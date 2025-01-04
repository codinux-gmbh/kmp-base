package net.codinux.kotlin.collections

import assertk.assertThat
import assertk.assertions.*
import net.codinux.kotlin.collections.CollectionsTestData.CountElements
import net.codinux.kotlin.collections.CollectionsTestData.ListTestData
import kotlin.test.Test

class OpenArrayListTest {

    @Test
    fun getSize() {
        val result = OpenArrayList(ListTestData)

        assertThat(result).hasSize(CountElements)
    }

    @Test
    fun isEmpty() {
        val result = OpenArrayList(emptyList<String>())

        assertThat(result).isEmpty()
    }

    @Test
    fun isNotEmpty() {
        val result = OpenArrayList(ListTestData)

        assertThat(result).isNotEmpty()
    }

    @Test
    fun iterationOrderRemains() {
        val result = OpenArrayList(ListTestData)

        CollectionsTestData.forAllElements { index ->
            assertThat(ListTestData[index]).isEqualTo(result[index])
        }
    }

    @Test
    fun indexOf() {
        val result = OpenArrayList(ListTestData)

        CollectionsTestData.forAllElements { index ->
            assertThat(result.indexOf(ListTestData[index])).isEqualTo(index)
        }
    }

    @Test
    fun contains() {
        val result = OpenArrayList(ListTestData)

        CollectionsTestData.forAllElements { index ->
            assertThat(result.contains(ListTestData[index])).isTrue()
        }
    }

    @Test
    fun containsAll() {
        val result = OpenArrayList(ListTestData)

        assertThat(result.containsAll(ListTestData)).isTrue()
    }
}