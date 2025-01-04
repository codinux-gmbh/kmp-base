package net.codinux.kotlin

import kotlinx.browser.window

actual object Platform {

    actual val type = PlatformType.WasmJs


    actual val lineSeparator: String = "\n"

    actual val fileSeparator: String = "/"


    actual val osName: String = window.navigator.platform

    actual val osVersion: String = window.navigator.userAgent

    actual val cpuArchitecture: String? = null

}