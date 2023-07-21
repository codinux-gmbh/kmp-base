package net.codinux.kotlin.internal

actual class Internal {

    actual companion object {

        actual fun getSystemLocale() =
            net.codinux.kotlin.platform.foundation.Locale.getDefaultLocale()

    }

}