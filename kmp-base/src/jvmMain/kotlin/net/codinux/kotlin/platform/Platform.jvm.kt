package net.codinux.kotlin.platform

actual object Platform {

    actual val type = PlatformType.JVM


    actual val lineSeparator = JavaPlatform.lineSeparator

    actual val fileSeparator = JavaPlatform.fileSeparator

    val pathSeparator = JavaPlatform.pathSeparator


    actual val osName = System.getProperty("os.name") ?: "Unknown" // what about "java.vm.vendor" or "java.vendor" (both "Arch Linux" on my machine)

    actual val osVersion = System.getProperty("os.version") ?: "Unknown"

    actual val cpuArchitecture: String? = System.getProperty("osArch")


    val userName = System.getProperty("user.name")

    val userLanguage = System.getProperty("user.language")

    val userCountry = System.getProperty("user.country")

    val userVariant = System.getProperty("user.variant")

    val userTimeZone = System.getProperty("user.timezone")


    val userHome = System.getProperty("user.home")

    val currentDir = System.getProperty("user.dir") // TODO: or convert to method to reflect always current state?

    val tmpDir = System.getProperty("java.io.tmpdir")


    val isRunningTestsWithGradle = System.getProperty("org.gradle.test.worker") != null

}