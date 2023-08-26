package net.codinux.kotlin

import net.codinux.collections.immutableListOf

enum class PlatformType {

    JVM,
    Android, // TODO: add platform Android
    JavaScriptBrowser,
    NodeJS,
    Linux,
    Windows,
    MacOS,
    iOS,
    tvOS,
    watchOS,
    WASM,
    Unknown;

    val DarwinPlatforms by lazy { immutableListOf(MacOS, iOS, tvOS, watchOS) }

    val isJvmOrAndroid by lazy { this == JVM || this == Android }

    val isJavaScript by lazy { this == JavaScriptBrowser || this == NodeJS }

    val isNative by lazy { this == Linux || this == Windows || isDarwin }

    val isLinuxOrMingw by lazy { this == Linux || this == Windows}

    val isDarwin by lazy { DarwinPlatforms.contains(this) }

    /**
     * Alias for [isDarwin]
     */
    val isAppleOS by lazy { isDarwin }

}