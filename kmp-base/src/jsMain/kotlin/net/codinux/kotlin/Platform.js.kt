package net.codinux.kotlin

import kotlinx.browser.window

actual object Platform {

    val isRunningInBrowser: Boolean by lazy { js("typeof window !== 'undefined'") }

    actual val type: PlatformType by lazy { if (isRunningInBrowser) PlatformType.JavaScriptBrowser else PlatformType.JavaScriptNodeJS }


    val os = if (isRunningInBrowser) null else js("require('os')")


    // For JavaScript targets (both Browser and Wasm), file system-related concepts don’t exist natively, but you can use default Unix-like conventions (\n and /)

    actual val lineSeparator: String = if (isRunningInBrowser) "\n" else os.EOL

    actual val fileSeparator: String = "/" // Node.js uses "/" even on Windows for path APIs


    actual val osName: String = if (isRunningInBrowser) window.navigator.platform ?: "Unknown" else os?.type() as String

    actual val osVersion: String = if (isRunningInBrowser) window.navigator.userAgent else os?.release() as String // JS doesn't provide detailed OS version

    actual val cpuArchitecture: String? = if (isRunningInBrowser) null else os.arch() as String


    init {
        if (isRunningInBrowser) {
            println("app = ${window.navigator.appName}, ${window.navigator.appCodeName}, ${window.navigator.appVersion}, " +
                    "product = ${window.navigator.product}, vendor = ${window.navigator.vendor} ${window.navigator.vendorSub}, " +
                    "lang = ${window.navigator.language}, ${window.navigator.oscpu}")
        }
    }

}