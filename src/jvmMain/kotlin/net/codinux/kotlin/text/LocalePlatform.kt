package net.codinux.kotlin.text

import net.codinux.collections.toImmutableList
import java.text.DecimalFormat

actual class LocalePlatform {

    actual companion object {

        private val AvailableJavaLocales by lazy { java.util.Locale.getAvailableLocales() }

        actual val AvailableLocales: List<Locale> by lazy {
            AvailableJavaLocales
                .map { mapLocale(it) }
                .toImmutableList()
        }

        actual fun getSystemLocale(): Locale {
            val jvmLocale = java.util.Locale.getDefault()

            return mapLocale(jvmLocale)
        }

        fun javaLocaleFromLanguageTag(languageTag: String): java.util.Locale? =
            AvailableJavaLocales.firstOrNull { it.toLanguageTag() == languageTag }

        private fun mapLocale(jvmLocale: java.util.Locale) = Locale(
            jvmLocale.language,
            jvmLocale.country,
            jvmLocale.script.takeIf { it.isNotBlank() },
            jvmLocale.variant.takeIf { it.isNotBlank() }
        )

        private fun getFormatSymbols(locale: java.util.Locale) {
            val decimalFormat = (DecimalFormat.getInstance(locale) as DecimalFormat)
            val symbols = decimalFormat.decimalFormatSymbols

//            return FormatSymbols(
//                symbols.decimalSeparator,
//                symbols.currencySymbol,
//                decimalFormat.maximumFractionDigits,
//                decimalFormat.minimumFractionDigits,
//                decimalFormat.maximumIntegerDigits,
//                decimalFormat.minimumIntegerDigits
//            )
        }

    }

}