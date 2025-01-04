package net.codinux.kotlin

import platform.Foundation.NSProcessInfo

internal object ApplePlatform {

    val lineSeparator: String = "\n"

    val fileSeparator: String = "/"


    val osName: String = NSProcessInfo.processInfo.operatingSystemName

    val osVersion: String = NSProcessInfo.processInfo.operatingSystemVersionString

    val cpuArchitecture: String? = platform.posix.uname().machine

}