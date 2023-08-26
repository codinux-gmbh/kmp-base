package net.codinux.kotlin.platform.foundation

import net.codinux.collections.toImmutableList
import net.codinux.kotlin.text.Locale
import platform.Foundation.*

class Locale {

    companion object {

        private val AvailableNSLocales by lazy {
            NSLocale.availableLocaleIdentifiers()
                .filterIsInstance<String>()
                .map { NSLocale(it) }
        }

        val AvailableLocales: List<Locale> by lazy {
            AvailableNSLocales
                .map { mapToUtilLocale(it) }
                .toImmutableList()
        }

        fun getDeviceLocale(): Locale =
            mapToUtilLocale(NSLocale.currentLocale) // this returns the Device language

        fun getAppLanguage(): String? =
            NSLocale.preferredLanguages().firstOrNull() as? String
                ?: NSBundle.mainBundle().preferredLocalizations().firstOrNull() as? String

        fun nsFormatterForLocale(locale: Locale, style: NSNumberFormatterStyle): NSNumberFormatter? =
            nsLocaleForLocale(locale)?.let { nsLocale ->
                NSNumberFormatter().apply {
                    this.locale = nsLocale
                    this.numberStyle = style
                }
            }

        fun nsLocaleForLocale(locale: Locale): NSLocale? =
            nsLocaleForLanguageTag(locale.languageTag)
                ?: nsLocaleForLanguageTag("${locale.language}_${locale.country}")

        fun nsLocaleForLanguageTag(languageTag: String): NSLocale? =
            AvailableNSLocales.firstOrNull { it.localeIdentifier == languageTag }

        private fun mapToUtilLocale(locale: NSLocale) =
            Locale(locale.languageCode, locale.countryCode ?: "", locale.scriptCode, locale.variantCode)
    }

}