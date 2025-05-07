package net.codinux.kotlin.platform

import assertk.assertThat
import assertk.assertions.isGreaterThan
import assertk.assertions.isTrue
import kotlin.test.Test

class EnvironmentTest {

    private val underTest = Environment()


    @Test
    fun printEnvironmentVariables() {
        println("${Platform.type} has ${underTest.variables.size} environment variables:")

        // browsers are sandboxed, they have no access to system's environment variables
        if (Platform.isBrowser == false) {
            assertThat(underTest.variables.size).isGreaterThan(20)
        }
    }

    @Test
    fun isRunningTests() {
        if (Platform.type != PlatformType.Linux) {
            assertThat(underTest.isRunningTests).isTrue()
        }
    }

}