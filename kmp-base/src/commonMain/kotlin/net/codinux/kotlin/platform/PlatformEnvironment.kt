package net.codinux.kotlin.platform

internal expect object PlatformEnvironment {

    fun getEnvironmentVariables(): Map<String, String>

}