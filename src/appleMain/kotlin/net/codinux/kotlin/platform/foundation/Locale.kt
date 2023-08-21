package net.codinux.kotlin.platform.foundation

import net.codinux.kotlin.text.Locale
import platform.Foundation.*

class Locale {

    companion object {

        val AvailableLocales: List<Locale> =
            NSLocale.availableLocaleIdentifiers()
                .filterIsInstance<String>()
                .map { mapToUtilLocale(it) }

        fun getDeviceLocale(): Locale =
            mapToUtilLocale(NSLocale.currentLocale) // this returns the Device language

        fun getAppLanguage(): String? =
            NSLocale.preferredLanguages().firstOrNull() as? String
                ?: NSBundle.mainBundle().preferredLocalizations().firstOrNull() as? String

        private fun mapToUtilLocale(languageTag: String) = mapToUtilLocale(NSLocale(languageTag))

        private fun mapToUtilLocale(locale: NSLocale) =
            Locale(locale.languageCode, locale.countryCode ?: "", locale.scriptCode, locale.variantCode)
    }

}