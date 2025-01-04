package net.codinux.kotlin

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSProcessInfo

internal object ApplePlatform {

    val lineSeparator: String = "\n"

    val fileSeparator: String = "/"


    private val processInfo = NSProcessInfo.processInfo

    /**
     * Returns only "NSMACHOperatingSystem", no matter if being called for macOS, iOS, ..., so don't use it
     */
    val osName: String = processInfo.operatingSystemName()

    val osVersion: String = processInfo.operatingSystemVersionString

    @OptIn(ExperimentalForeignApi::class)
    val cpuArchitecture: String? = null // platform.posix.uname().machine


    val userName = processInfo.environment["USER"]

    val logName = processInfo.environment["LOGNAME"]

    val userHome = processInfo.environment["HOME"]

}