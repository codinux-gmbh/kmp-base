package net.codinux.kotlin.internal

import net.codinux.collections.immutableListOf
import net.codinux.kotlin.util.Locale

actual class Internal {

    actual companion object {

        // available locales may can be read from /usr/share/i18n/SUPPORTED, but that's of course not an robust and universe way to get all locales
        // "locale -a" didn't work on my system
        // This is may due to not all locales have been generated, see "cat /etc/locale.gen" for available and currently unavailable locales
        actual val AvailableLocales: List<Locale> = immutableListOf()

        actual fun getSystemLocale() =
            net.codinux.kotlin.platform.posix.Locale.getSystemLocale()

    }

}