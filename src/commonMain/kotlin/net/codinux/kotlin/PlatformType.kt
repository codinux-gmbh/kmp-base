package net.codinux.kotlin

import net.codinux.kotlin.collections.ImmutableList

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

    val DarwinPlatforms by lazy { ImmutableList(MacOS, iOS, tvOS, watchOS) }

    val isJavaScript by lazy { this == JavaScriptBrowser || this == NodeJS }

    val isNative by lazy { this == Linux || this == Windows || isDarwin }

    val isDarwin by lazy { DarwinPlatforms.contains(this) }

    /**
     * Alias for [isDarwin]
     */
    val isAppleOS by lazy { isDarwin }

}