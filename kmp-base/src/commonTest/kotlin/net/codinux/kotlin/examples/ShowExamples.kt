package net.codinux.kotlin.examples

import net.codinux.kotlin.platform.*
import net.codinux.kotlin.text.*

class ShowExamples {

    fun platformType() {
        println("Running on platform ${Platform.type}") // returns the type of the platform like JVM, iOS, Linux, JsBrowser, ...

        Platform.isAndroidOrIOS // see also the other Platform.isXyz convenience methods to determine platform type
        Platform.isNative
        Platform.isJavaScript
    }

    fun getSystemInformation() {
        println("Platform line separator: ${Platform.lineSeparator}")
        println("Platform file separator: ${Platform.fileSeparator}")

        println("OS name: ${Platform.osName}")
        println("OS version: ${Platform.osVersion}")
        println("CPU architecture: ${Platform.cpuArchitecture}")
    }

    fun getEnvironmentVariables() {
        val environment = Environment()

        println("Environment variables:")
        environment.variables.forEach { (name, value) ->
            println("$name: $value")
        }

        // not very reliable but a good hint on most platforms
        println("Is running in debug mode: ${environment.isRunningInDebugMode}")
        println("Is running tests: ${environment.isRunningTests}")
    }

    fun stringExtensions() {
        null.isNotNullOrBlank()

        "test-string".indexOfOrNull("fun") // returns `null` instead of `-1` if string is not found

        "another-test-string".indicesOf("-") // returns [7, 12]

        "another-test-string".count("-") // returns 2

        "test-string".ofMaxLength(4) // returns a string of max length 4 if the string is longer then 4, otherwise the original string

        // see also the other .substringXyz() methods
        "test".substringBeforeOrNull("fun") // returns `null` instead of `-1` if string "fun" is not found
        "test".substringAfterLastOrNull("fun") // returns `null` instead of `-1` if string "fun" is not found
    }

}