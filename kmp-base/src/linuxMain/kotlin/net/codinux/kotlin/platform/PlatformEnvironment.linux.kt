package net.codinux.kotlin.platform

import kotlinx.cinterop.*
import platform.posix.*

internal actual object PlatformEnvironment {

    actual val isRunningTests = false // don't know how to get this in native mode


    @OptIn(ExperimentalForeignApi::class)
    actual fun getEnvironmentVariables(): Map<String, String> {
        val environmentVariables = mutableMapOf<String, String>()

        try {
            var environPointer = __environ // Start at the beginning of the environment block

            while (true) {
                val envEntryPointer = environPointer?.pointed?.value // Dereference to get the current entry
                if (envEntryPointer == null) {
                    break  // Stop if we reach the end (null entry)
                }

                val envEntry = envEntryPointer.toKString()
                val (key, value) = envEntry.split("=", limit = 2)
                environmentVariables[key] = value

                environPointer = environPointer.plus(1) // Move to the next entry
            }
        } catch (e: Throwable) {
            println("Could not get environment variables of Linux system: $e")
        }

        return environmentVariables
    }

}