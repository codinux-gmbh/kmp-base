package net.codinux.kotlin.platform.foundation

import net.codinux.kotlin.text.Locale
import platform.Foundation.*

class Locale {

    companion object {

        val AvailableNSLocales = NSLocale.availableLocaleIdentifiers()
            .filterIsInstance<String>()
            .map { NSLocale(it) }

        val AvailableLocales: List<Locale> = AvailableNSLocales
                .map { mapToUtilLocale(it) }

        fun getDeviceLocale(): Locale =
            mapToUtilLocale(NSLocale.currentLocale) // this returns the Device language

        fun getAppLanguage(): String? =
            NSLocale.preferredLanguages().firstOrNull() as? String
                ?: NSBundle.mainBundle().preferredLocalizations().firstOrNull() as? String

        fun nsLocaleFromLanguageTag(languageTag: String): NSLocale? =
            AvailableNSLocales.firstOrNull { it.localeIdentifier == languageTag }

        private fun mapToUtilLocale(locale: NSLocale) =
            Locale(locale.languageCode, locale.countryCode ?: "", locale.scriptCode, locale.variantCode)
    }

}