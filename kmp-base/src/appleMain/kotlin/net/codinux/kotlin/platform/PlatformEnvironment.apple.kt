package net.codinux.kotlin.platform

import platform.Foundation.NSProcessInfo

import net.codinux.kotlin.ApplePlatform.processInfo
import platform.Foundation.NSProcessInfo

internal actual object PlatformEnvironment {

    actual val isRunningTests = NSProcessInfo.processInfo.environment["XCTestConfigurationFilePath"] != null


    actual fun getEnvironmentVariables(): Map<String, String> =
        NSProcessInfo.processInfo.environment

}