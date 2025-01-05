package net.codinux.kotlin.platform

import android.content.pm.ApplicationInfo
import net.codinux.kotlin.android.AndroidContext

internal actual object JavaPlatformExpects {

    actual val isRunningInDebugMode: Boolean =
        AndroidContext.getApplicationContextIfInitialized()?.let { applicationContext ->
            // BuildConfig.DEBUG will always be false as for a compiled library its set to false at compile time
            val applicationInfo = applicationContext.applicationContext.applicationInfo
            applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } ?: false

}