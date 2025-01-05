package net.codinux.kotlin.platform

internal expect object PlatformEnvironment {

    val isRunningTests: Boolean

    val isRunningInDebugMode: Boolean


    fun getEnvironmentVariables(): Map<String, String>

}