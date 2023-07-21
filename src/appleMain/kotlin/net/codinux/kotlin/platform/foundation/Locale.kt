package net.codinux.kotlin.platform.foundation

import net.codinux.kotlin.util.Locale
import platform.Foundation.*

class Locale {

    companion object {

        fun getDefaultLocale(): Locale =
            mapToUtilLocale(NSLocale.currentLocale)

        fun getAvailableLocales(): List<Locale> =
            NSLocale.availableLocaleIdentifiers()
                .filterIsInstance<NSLocale>()
                .map { mapToUtilLocale(it) }

        private fun mapToUtilLocale(locale: NSLocale) =
            Locale(locale.languageCode, locale.countryCode ?: "", locale.variantCode, locale.scriptCode)
    }

}