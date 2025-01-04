package net.codinux.kotlin

actual object Platform {

    val isRunningInBrowser: Boolean by lazy { js("typeof window !== 'undefined'") }

    actual val type: PlatformType by lazy { if (isRunningInBrowser) PlatformType.JavaScriptBrowser else PlatformType.JavaScriptNodeJS }

}