package net.codinux.kotlin.platform

import kotlinx.cinterop.*
import platform.windows.*

internal actual object PlatformEnvironment {

    @OptIn(ExperimentalForeignApi::class)
    actual fun getEnvironmentVariables(): Map<String, String> {
        val environmentStrings = GetEnvironmentStringsW() ?: return emptyMap()

        val environmentVariables = mutableMapOf<String, String>()

        try {
            var envPointer: CPointer<UShortVar>? = environmentStrings
            while (envPointer != null) {
                val entry = envPointer.toKStringFromUtf16()
                if (entry.isEmpty()) {
                    break
                }

                val (key, value) = entry.split("=", limit = 2)
                environmentVariables[key] = value

                envPointer += entry.length + 1 // Move to the next entry
            }
        } finally {
            FreeEnvironmentStringsW(environmentStrings)
        }

        return environmentVariables
    }

}