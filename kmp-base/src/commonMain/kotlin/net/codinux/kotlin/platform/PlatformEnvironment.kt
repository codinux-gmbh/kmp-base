package net.codinux.kotlin.platform

internal expect object PlatformEnvironment {

    val isRunningTests: Boolean

    fun getEnvironmentVariables(): Map<String, String>

}