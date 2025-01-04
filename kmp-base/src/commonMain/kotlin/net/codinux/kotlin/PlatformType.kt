package net.codinux.kotlin

enum class PlatformType {

    JVM,
    Android,
    JavaScriptBrowser,
    JavaScriptNodeJS,
    WasmJs,
    Linux,
    Windows,
    macOS,
    iOS,
    tvOS,
    watchOS
    ;


    private val DarwinPlatforms by lazy { setOf(macOS, iOS, tvOS, watchOS) }

    val isJvmOrAndroid by lazy { this == JVM || this == Android }

    val isJavaScript by lazy { this == JavaScriptBrowser || this == JavaScriptNodeJS }

    val isNative by lazy { this == Linux || this == Windows || isDarwin }

    val isLinuxOrMingw by lazy { this == Linux || this == Windows}

    val isDarwin by lazy { DarwinPlatforms.contains(this) }

    /**
     * Alias for [isDarwin]
     */
    val isAppleOS by lazy { isDarwin }

}