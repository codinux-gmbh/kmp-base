package net.codinux.kotlin.platform

internal actual object PlatformEnvironment {

    actual fun getEnvironmentVariables(): Map<String, String> =
        System.getenv() + System.getProperties().stringPropertyNames().associateWith { System.getProperty(it) }

}