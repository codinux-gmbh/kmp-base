package net.codinux.kotlin.text

import net.codinux.collections.toImmutableList
import net.codinux.collections.toImmutableMap
import net.codinux.kotlin.util.IntlLocale
import net.codinux.kotlin.util.IntlNumberFormat
import net.codinux.kotlin.util.WeekInfo

actual class LocalePlatform {

    actual companion object {

        // JavaScript has no method to get all supported Locales. But as it uses ICU, we can iterate over all
        // Locales from ICU to find all JS Intl.Locale instances
        private val AvailableIntlLocales: Map<String, IntlLocale> by lazy {
            ICU.AvailableLocales
                .map { (languageTag, _) -> languageTag}
                .associateWith { getIntlLocaleForLanguageTag(it) }
                .filterValues { it != null }
                .toImmutableMap() as Map<String, IntlLocale>
        }

        actual val AvailableLocales: List<Locale> by lazy {
            AvailableIntlLocales.map { (_, intlLocale) ->
                getLocaleForIntLocale(intlLocale)
            }.toImmutableList()
        }

        actual fun getSystemLocale(): Locale {
            val localeString = js("Intl.NumberFormat().resolvedOptions().locale")

            return getLocaleForLanguageTag(localeString)!!
        }

        private fun getIntlLocaleForLanguageTag(languageTag: String): IntlLocale? {
            return try {
                eval("new Intl.Locale(\"$languageTag\")").unsafeCast<IntlLocale?>()
            } catch (ignored: Throwable) {
                null
            }
        }

        private fun getLocaleForLanguageTag(languageTag: String) = getIntlLocaleForLanguageTag(languageTag)?.let { locale ->
            Locale(locale.language, locale.region, locale.script)
        }

        private fun getLocaleForIntLocale(locale: IntlLocale) =
            Locale(locale.language, locale.region, locale.script)

        fun getIntlNumberFormatForLocale(locale: Locale, options: String): IntlNumberFormat? =
            try {
                eval("Intl.NumberFormat('${locale.languageTag}', { $options })")
                    .unsafeCast<IntlNumberFormat?>()
            } catch (ignored: Throwable) {
                null
            }

        private fun formatCurrency(locale: IntlLocale) {
            // val options = IntlNumberFormatOptions()
//            val currencyFormat = eval("Intl.NumberFormat('${numberFormat.locale}', { style: 'currency' }).resolvedOptions()").unsafeCast<IntlNumberFormat>()


            println("${locale.language}, ${locale.region}, ${locale.script}, ${locale.baseName}, ${locale.numberingSystem}, ${locale.numeric}, ${locale.hourCycle}, ${locale.calendar}, ${locale.collation}, ${locale.caseFirst}")
            println("${locale.textInfos}, ${locale.numberingSystems}, ${locale.calendars}, ${locale.weekInfo}, ${locale.timeZones}, ${locale.hourCycles}, ${locale.collations}")
            println("${locale.maximize()}, ${locale.minimize()}")
            println("${locale.toString()}")

            val weekInfo = locale.weekInfo?.unsafeCast<WeekInfo>()
            println("${weekInfo!!::class}, ${weekInfo!!.firstDay}")

//            return FormatSymbols(
//                null,
//                null,
//                numberFormat.maximumFractionDigits,
//                numberFormat.minimumFractionDigits,
//                null,
//                numberFormat.minimumIntegerDigits
//            )
        }

    }

}