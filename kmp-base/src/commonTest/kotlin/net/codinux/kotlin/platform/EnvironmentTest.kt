package net.codinux.kotlin.platform

import net.codinux.kotlin.Platform
import kotlin.test.Test

class EnvironmentTest {

    private val underTest = Environment()


    @Test
    fun printEnvironmentVariables() {
        println("${Platform.type} Environment variables:")

        underTest.variables.forEach { (name, value) ->
            println("$name: $value")
        }
    }

}