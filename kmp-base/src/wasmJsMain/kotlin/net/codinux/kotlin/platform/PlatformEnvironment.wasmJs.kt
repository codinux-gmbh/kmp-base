package net.codinux.kotlin.platform

internal actual object PlatformEnvironment {

    // browsers are sandboxed, they have no access to system's environment variables
    actual fun getEnvironmentVariables(): Map<String, String> = emptyMap()

}