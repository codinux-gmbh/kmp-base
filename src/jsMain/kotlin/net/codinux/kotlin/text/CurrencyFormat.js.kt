package net.codinux.kotlin.text

import net.codinux.kotlin.util.IntlNumberFormat

actual class CurrencyFormat(private val currencyFormat: IntlNumberFormat) {

    actual companion object {

        actual fun getForLocale(locale: Locale): CurrencyFormat? {
            val currencyIsoCode = ICU.LocaleToCurrencyIsoCode[locale.languageTag]
            if (currencyIsoCode == null) {
                return null
            }

            return eval("Intl.NumberFormat('${locale.languageTag}', { style: 'currency', currency: '$currencyIsoCode' })")
                .unsafeCast<IntlNumberFormat?>()
                ?.let { CurrencyFormat(it) }
        }

    }


    actual fun format(currencyValue: Double): String? {
        return currencyFormat.format(currencyValue)
            ?.replace(' ', ' ') // JS uses non-breaking space as white space separator -> normalize to make equal to other platforms
    }

}