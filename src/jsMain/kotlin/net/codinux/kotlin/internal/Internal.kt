package net.codinux.kotlin.internal

import net.codinux.collections.toImmutableList
import net.codinux.kotlin.text.ICU
import net.codinux.kotlin.util.IntlLocale
import net.codinux.kotlin.text.Locale
import net.codinux.kotlin.util.WeekInfo

actual class Internal {

    actual companion object {

        // JavaScript has no method to get all supported Locales. But as it uses ICU, we can iterate over all
        // Locales from ICU to find all JS Intl.Locale instances
        actual val AvailableLocales: List<Locale> = ICU.AvailableLocales.mapNotNull { (languageTag, _) ->
            getLocaleForLanguageTag(languageTag)
        }.toImmutableList()

        actual fun getSystemLocale(): Locale {
            val localeString = js("Intl.NumberFormat().resolvedOptions().locale")

            return getLocaleForLanguageTag(localeString)!!
        }

        private fun getLocaleForLanguageTag(languageTag: String): Locale? {
            try {
                eval("new Intl.Locale(\"$languageTag\")").unsafeCast<IntlLocale?>()?.let { locale ->
                    return Locale(locale.language, locale.region, locale.script)
                }
            } catch (ignored: Exception) { }

            return null
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