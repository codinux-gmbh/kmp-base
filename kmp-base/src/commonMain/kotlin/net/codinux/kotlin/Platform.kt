package net.codinux.kotlin

import net.codinux.kotlin.Platform.type
import net.codinux.kotlin.PlatformType.*

expect object Platform {

    val type: PlatformType


    val lineSeparator: String

    val fileSeparator: String


    val osName: String

    val osVersion: String

    val cpuArchitecture: String?

}


private val DarwinPlatforms by lazy { setOf(macOS, iOS, tvOS, watchOS) }

val Platform.isJvm: Boolean
    get() = this.type == PlatformType.JVM

val Platform.isAndroid: Boolean
    get() = this.type == PlatformType.Android

val Platform.isJvmOrAndroid
    get() = isJvm || isAndroid

val isJavaScript
    get() = type == JavaScriptBrowser || type == JavaScriptNodeJS

val isNative
    get() = type == Linux || type == Windows || isDarwin

val isLinuxOrMingw
    get() = type == Linux || type == Windows

val isDarwin
    get() = DarwinPlatforms.contains(type)

/**
 * Alias for [isDarwin]
 */
val isAppleOS
    get() = isDarwin

val Platform.isIOS: Boolean
    get() = this.type == PlatformType.iOS