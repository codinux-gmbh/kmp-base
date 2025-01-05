package net.codinux.kotlin.platform

import platform.Foundation.NSProcessInfo

import net.codinux.kotlin.ApplePlatform.processInfo
import platform.Foundation.NSProcessInfo
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
internal actual object PlatformEnvironment {

    actual val isRunningTests = NSProcessInfo.processInfo.environment["XCTestConfigurationFilePath"] != null

    actual val isRunningInDebugMode = Platform.isDebugBinary


    actual fun getEnvironmentVariables(): Map<String, String> =
        NSProcessInfo.processInfo.environment

}