package net.codinux.kotlin

actual object Platform {

    actual val type = PlatformType.iOS


    actual val lineSeparator = ApplePlatform.lineSeparator

    actual val fileSeparator = ApplePlatform.fileSeparator


    actual val osName = "iOS"

    actual val osVersion = ApplePlatform.osVersion

    actual val cpuArchitecture: String? = ApplePlatform.cpuArchitecture

}