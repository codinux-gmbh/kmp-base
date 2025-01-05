package net.codinux.kotlin

import java.io.File

internal object JavaPlatform {

    val lineSeparator = System.lineSeparator()

    val fileSeparator = File.separator

    val pathSeparator = File.pathSeparatorChar


    val isRunningOnAndroid by lazy { isClassAvailable("android.content.Context") }

    fun isClassAvailable(qualifiedClassName: String): Boolean =
        getClassOrNull(qualifiedClassName) != null

    fun getClassOrNull(qualifiedClassName: String): Class<*>? =
        try {
            Class.forName(qualifiedClassName)
        } catch (ignored: Exception) {
            null
        }

}