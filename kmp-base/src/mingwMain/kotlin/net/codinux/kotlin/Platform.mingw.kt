package net.codinux.kotlin

import kotlinx.cinterop.*
import platform.windows.*
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
actual object Platform {

    actual val type = PlatformType.Windows


    actual val lineSeparator: String = "\r\n"

    actual val fileSeparator: String = "\\"


    val osInfo = memScoped {
        alloc<_OSVERSIONINFOW>().apply {
            dwOSVersionInfoSize = sizeOf<OSVERSIONINFOEXW>().toUInt()
        }
    }

    init {
        if (GetVersionExW(osInfo.ptr) == 0) {
            throw RuntimeException("Failed to get Windows version")
        }
    }

    actual val osName: String = "Windows"

    actual val osVersion: String = "${osInfo.dwMajorVersion}.${osInfo.dwMinorVersion} (Build ${osInfo.dwBuildNumber})"

    actual val cpuArchitecture: String? = Platform.cpuArchitecture.name.lowercase()

}