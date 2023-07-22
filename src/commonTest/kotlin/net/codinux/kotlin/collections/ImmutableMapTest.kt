package net.codinux.kotlin.collections

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import net.codinux.kotlin.collections.CollectionsTestData.CountElements
import net.codinux.kotlin.collections.CollectionsTestData.MapTestData
import net.codinux.kotlin.collections.CollectionsTestData.forAllElements
import kotlin.test.Test

class ImmutableMapTest {

    @Test
    fun getSize() {
        val underTest = ImmutableMap(MapTestData)

        underTest.shouldHaveSize(CountElements)
    }

    @Test
    fun isEmpty() {
        val underTest = ImmutableMap(emptyMap<String, String>())

        underTest.isEmpty().shouldBeTrue()
    }

    @Test
    fun isNotEmpty() {
        val underTest = ImmutableMap(MapTestData)

        underTest.isNotEmpty().shouldBeTrue()
    }

    @Test
    fun keys() {
        val underTest = ImmutableMap(MapTestData)

        underTest.keys.shouldContainAll(0, 1 ,2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    }

    @Test
    fun values() {
        val underTest = ImmutableMap(MapTestData)

        underTest.values.shouldContainAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
    }

    @Test
    fun get() {
        val underTest = ImmutableMap(MapTestData)

        forAllElements { index ->
            underTest.get(index).shouldBe(index.toString())
        }
    }

    @Test
    fun containsKey() {
        val underTest = ImmutableMap(MapTestData)

        forAllElements { index ->
            underTest.containsKey(index).shouldBeTrue()
        }
    }

    @Test
    fun containsValue() {
        val underTest = ImmutableMap(MapTestData)

        forAllElements { index ->
            underTest.containsValue(index.toString()).shouldBeTrue()
        }
    }

}