package net.codinux.kotlin.platform

import net.codinux.kotlin.android.AndroidContext

internal actual object JavaPlatformExpects {

    actual val isRunningInDebugMode: Boolean
        // BuildConfig.DEBUG will always be false as for a compiled library its set to false at compile time
        AndroidContext.applicationContext?.applicationContext?.let {
            it.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } ?: false

}