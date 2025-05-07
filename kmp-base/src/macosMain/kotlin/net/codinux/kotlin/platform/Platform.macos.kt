package net.codinux.kotlin.platform

actual object Platform {

    actual val type = PlatformType.macOS


    actual val lineSeparator = ApplePlatform.lineSeparator

    actual val fileSeparator = ApplePlatform.fileSeparator


    actual val osName = "macOS"

    actual val osVersion = ApplePlatform.osVersion

    actual val cpuArchitecture: String? = ApplePlatform.cpuArchitecture

}