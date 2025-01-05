package net.codinux.kotlin

import platform.Foundation.NSProcessInfo
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

@OptIn(ExperimentalNativeApi::class)
internal object ApplePlatform {

    val lineSeparator: String = "\n"

    val fileSeparator: String = "/"


    private val processInfo = NSProcessInfo.processInfo

    // processInfo.operatingSystemName() returns only "NSMACHOperatingSystem", no matter if being called for
    // macOS, iOS, ..., so we cannot rely on it
    val osName: String = Platform.osFamily.name

    val osVersion: String = processInfo.operatingSystemVersionString

    val cpuArchitecture: String? = Platform.cpuArchitecture.name.lowercase()


    val userName = processInfo.environment["USER"]

    val logName = processInfo.environment["LOGNAME"]

    val userHome = processInfo.environment["HOME"]

}