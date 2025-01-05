package net.codinux.kotlin.platform

import platform.Foundation.NSProcessInfo

internal actual object PlatformEnvironment {

    actual fun getEnvironmentVariables(): Map<String, String> =
        NSProcessInfo.processInfo.environment

}