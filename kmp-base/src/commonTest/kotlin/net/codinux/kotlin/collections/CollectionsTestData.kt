package net.codinux.kotlin.collections

object CollectionsTestData {

    const val CountElements = 12

    val ListTestData = mutableListOf<String>().apply {
        forAllElements { index ->
            add(index.toString())
        }
    }

    val SetTestData = mutableSetOf<String>().apply {
        forAllElements { index ->
            add(index.toString())
        }
    }

    val MapTestData = mutableMapOf<Int, String>().apply {
        forAllElements { index ->
            put(index, index.toString())
        }
    }

    fun forAllElements(action: (index: Int) -> Unit) {
        for (index in 0 until CountElements) {
            action(index)
        }
    }

}