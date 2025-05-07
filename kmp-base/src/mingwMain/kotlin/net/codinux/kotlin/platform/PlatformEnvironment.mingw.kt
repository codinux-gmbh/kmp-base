package net.codinux.kotlin.platform

import kotlinx.cinterop.*
import platform.windows.*
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
internal actual object PlatformEnvironment {

    actual val isRunningTests = false // don't know how to get this in native mode

    actual val isRunningInDebugMode = kotlin.native.Platform.isDebugBinary


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