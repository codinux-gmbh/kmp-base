package net.codinux.kotlin.platform

import platform.Foundation.NSProcessInfo
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
internal actual object PlatformEnvironment {

    // TODO: does not work, XCTestConfigurationFilePath is never set when running tests with Gradle
    actual val isRunningTests = NSProcessInfo.processInfo.environment["XCTestConfigurationFilePath"] != null

    actual val isRunningInDebugMode = Platform.isDebugBinary


    actual fun getEnvironmentVariables(): Map<String, String> =
        NSProcessInfo.processInfo.environment.entries.associateBy({ it.key.toString() }, { it.value.toString() })

}