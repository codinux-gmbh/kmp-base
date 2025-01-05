package net.codinux.kotlin.platform

internal actual object PlatformEnvironment {

    actual val isRunningTests = System.getProperty("org.gradle.test.worker") != null


    actual fun getEnvironmentVariables(): Map<String, String> =
        System.getenv() + System.getProperties().stringPropertyNames().associateWith { System.getProperty(it) }

}