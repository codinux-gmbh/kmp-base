package net.codinux.kotlin

import android.os.Build

actual object Platform {

    actual val type = PlatformType.Android


    actual val osName: String = "Android"

    actual val osVersion: String = "${Build.VERSION.SDK_INT} (${Build.VERSION.RELEASE})"

    actual val cpuArchitecture: String? = Build.SUPPORTED_ABIS?.joinToString(", ")

}