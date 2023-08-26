package net.codinux.kotlin.text

import net.codinux.collections.toImmutableList
import net.codinux.kotlin.util.IntlLocale
import net.codinux.kotlin.util.WeekInfo

actual class LocalePlatform {

    actual companion object {

        // JavaScript has no method to get all supported Locales. But as it uses ICU, we can iterate over all
        // Locales from ICU to find all JS Intl.Locale instances
        actual val AvailableLocales: List<Locale> by lazy {
            ICU.AvailableLocales.mapNotNull { (languageTag, _) ->
                getLocaleForLanguageTag(languageTag)
            }.toImmutableList()
        }

        actual fun getSystemLocale(): Locale {
            val localeString = js("Intl.NumberFormat().resolvedOptions().locale")

            return getLocaleForLanguageTag(localeString)!!
        }

        private fun getIntlLocaleForLanguageTag(languageTag: String): IntlLocale? {
            return try {
                eval("new Intl.Locale(\"$languageTag\")").unsafeCast<IntlLocale?>()
            } catch (ignored: Exception) {
                null
            }
        }

        private fun getLocaleForLanguageTag(languageTag: String) = getIntlLocaleForLanguageTag(languageTag)?.let { locale ->
            Locale(locale.language, locale.region, locale.script)
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