package net.codinux.kotlin.platform.foundation

import net.codinux.kotlin.util.Locale
import platform.Foundation.*

class Locale {

    companion object {

        actual val availableLocales: List<Locale> =
            NSLocale.availableLocaleIdentifiers()
                .filterIsInstance<NSLocale>()
                .map { mapToUtilLocale(it) }

        fun getDefaultLocale(): Locale =
            mapToUtilLocale(NSLocale.currentLocale) // this returns the Device language
        // for App language use: Locale.preferredLanguages[0]

        private fun mapToUtilLocale(locale: NSLocale) =
            Locale(locale.languageCode, locale.countryCode ?: "", locale.variantCode, variant = locale.scriptCode)

        fun formatCurrency(value: Double): String? {
            // here's an example how to format a NSNumber as currency:
            // let formatter = NumberFormatter()
            // formatter.numberStyle = .currency
            // // formatter.locale = NSLocale.current
            // formatter.string(from: price) // "$123.44"

            // for all options see: https://developer.apple.com/documentation/foundation/numberformatter

            return null
        }
    }

}