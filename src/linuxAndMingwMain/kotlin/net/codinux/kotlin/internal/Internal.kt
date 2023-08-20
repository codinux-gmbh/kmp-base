package net.codinux.kotlin.internal

import net.codinux.collections.immutableListOf
import net.codinux.kotlin.util.Locale

actual class Internal {

    actual companion object {

        actual val AvailableLocales: List<Locale> = immutableListOf()

        actual fun getSystemLocale() =
            net.codinux.kotlin.platform.posix.Locale.getSystemLocale()

    }

}