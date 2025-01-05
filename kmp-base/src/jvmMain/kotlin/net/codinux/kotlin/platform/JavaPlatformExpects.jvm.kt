package net.codinux.kotlin.platform

import net.codinux.kotlin.JavaPlatform
import java.lang.management.ManagementFactory

internal actual object JavaPlatformExpects {

    actual val isRunningInDebugMode: Boolean by lazy {
        JavaPlatform.isRunningOnAndroid == false && // due to a bug in Gradle(?) Android library doesn't get published so that Android calls this JVM code leading to crashes when running on an Android device
                isDebuggingEnabled()
    }


    private fun isDebuggingEnabled(): Boolean =
        try {
            JavaPlatform.isClassAvailable("java.lang.management.ManagementFactory") &&
                    // not 100 % reliable, but the best i could find, see e.g. https://stackoverflow.com/questions/28754627/check-whether-we-are-in-intellij-idea-debugger
                    ManagementFactory.getRuntimeMXBean().inputArguments.any { it.contains("jdwp", true) }
        } catch (e: Throwable) {
            println("Could not determine if debugging is enabled, $e")
            false
        }


}