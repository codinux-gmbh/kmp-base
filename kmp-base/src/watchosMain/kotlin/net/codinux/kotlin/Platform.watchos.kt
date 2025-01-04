package net.codinux.kotlin

actual object Platform {

    actual val type = PlatformType.watchOS


    actual val osName = ApplePlatform.osName

    actual val osVersion = ApplePlatform.osVersion

    actual val cpuArchitecture: String? = ApplePlatform.cpuArchitecture

}