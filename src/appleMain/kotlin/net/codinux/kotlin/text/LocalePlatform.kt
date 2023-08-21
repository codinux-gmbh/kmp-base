package net.codinux.kotlin.text

actual class LocalePlatform {

    actual companion object {

        actual val AvailableLocales: List<Locale> =
            net.codinux.kotlin.platform.foundation.Locale.availableLocales

        actual fun getSystemLocale() =
            net.codinux.kotlin.platform.foundation.Locale.getDefaultLocale()

    }

}