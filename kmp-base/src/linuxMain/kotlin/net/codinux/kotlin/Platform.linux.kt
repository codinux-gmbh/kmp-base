package net.codinux.kotlin

import kotlinx.cinterop.*
import platform.posix.uname

@OptIn(ExperimentalForeignApi::class)
actual object Platform {

    actual val type = PlatformType.Linux


    actual val lineSeparator: String = "\n"

    actual val fileSeparator: String = "/"


    private var sysname: String = "Linux"

    val utsName = memScoped {
        val utsName = alloc<platform.posix.utsname>()
        uname(utsName.ptr)

        // sysname = Name of this implementation of the operating system.
        sysname = utsName.sysname.toKString() // utsName.sysname prints only nonsense after leaving memScoped

        utsName
    }

    actual val osName: String = sysname

    // utsName.version = Current version level of this release, returned the date time of the build on my machine
    actual val osVersion: String = utsName.release.toKString() // Current release level of this implementation.

    // utsName.machine == "x86_64", Platform.cpuArchitecture = "X64"
    actual val cpuArchitecture: String? = utsName.machine.toKString() // Name of the hardware type on which the system is running.
        //?: Platform.cpuArchitecture.name.lowercase()

    val hostName: String = utsName.nodename.toKString() // Name of this node within the communications network to which this node is attached, if any.

}