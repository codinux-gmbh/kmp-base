package net.codinux.kotlin.text

import java.text.DecimalFormat
import java.text.NumberFormat

actual class CurrencyFormat(private val currencyFormat: DecimalFormat) {

    actual companion object {
        actual fun getForLocale(locale: Locale): CurrencyFormat? =
            (NumberFormat.getCurrencyInstance(java.util.Locale.forLanguageTag(locale.languageTag)) as? DecimalFormat)?.let {
                CurrencyFormat(it)
            }
    }

    actual fun format(currencyValue: Double): String? =
        currencyFormat.format(currencyValue)
            .replace("SFr.", "CHF")

}