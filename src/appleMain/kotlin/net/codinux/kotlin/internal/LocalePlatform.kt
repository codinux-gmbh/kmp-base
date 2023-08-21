package net.codinux.kotlin.internal

actual class LocalePlatform {

    actual companion object {

        actual fun getSystemLocale() =
            net.codinux.kotlin.platform.foundation.Locale.getDefaultLocale()

    }

}