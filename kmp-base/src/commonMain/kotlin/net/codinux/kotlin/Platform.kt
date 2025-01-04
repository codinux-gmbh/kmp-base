package net.codinux.kotlin

expect object Platform {

    val type: PlatformType


    val osName: String

    val osVersion: String

    val cpuArchitecture: String?

}