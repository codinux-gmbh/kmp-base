package net.codinux.kotlin

import kotlin.native.Platform

actual object Platform {

    actual val type: PlatformType by lazy { determinePlatformType() }

    private fun determinePlatformType() = when (Platform.osFamily) {
        OsFamily.ANDROID -> PlatformType.Android
        OsFamily.LINUX -> PlatformType.Linux
        OsFamily.WINDOWS -> PlatformType.Windows
        OsFamily.MACOSX -> PlatformType.MacOS
        OsFamily.IOS -> PlatformType.iOS
        OsFamily.TVOS -> PlatformType.tvOS
        OsFamily.WATCHOS -> PlatformType.watchOS
        OsFamily.WASM -> PlatformType.WASM
        OsFamily.UNKNOWN -> PlatformType.Unknown
    }

}