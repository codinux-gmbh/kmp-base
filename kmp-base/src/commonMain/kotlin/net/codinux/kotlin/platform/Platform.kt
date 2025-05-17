package net.codinux.kotlin.platform

import net.codinux.kotlin.platform.PlatformType.*

expect object Platform {

    val type: PlatformType


    val lineSeparator: String

    val fileSeparator: String


    val osName: String

    val osVersion: String

    val cpuArchitecture: String?

}


private val ApplePlatforms = setOf(macOS, iOS, tvOS, watchOS)

val Platform.isJvm: Boolean
    get() = this.type == PlatformType.JVM

val Platform.isAndroid: Boolean
    get() = this.type == PlatformType.Android

val Platform.isJvmOrAndroid
    get() = isJvm || isAndroid

/**
 * Returns `true` if running in JS/Browser or JS/Node.
 */
val Platform.isJsBrowserOrNodeJs
    get() = type == JsBrowser || type == JsNodeJs

/**
 * Returns `true` if running in JS/Browser, JS/Node or WASM.
 */
val Platform.isJavaScript
    get() = isJsBrowserOrNodeJs || type == WasmJs

/**
 * Returns `true` if running in JS/Browser or WasmJs/Browser.
 */
val Platform.isBrowser // there is also Plaform.js.isRunningInBrowser
    get() = type == JsBrowser || type == WasmJs

val Platform.isNative
    get() = type == Linux || type == Windows || isAppleOS

val Platform.isLinuxOrMingw
    get() = type == Linux || type == Windows

val Platform.isAppleOS
    get() = ApplePlatforms.contains(type)

val Platform.isIOS: Boolean
    get() = this.type == PlatformType.iOS

val Platform.isAndroidOrIOS: Boolean
    get() = isAndroid || isIOS