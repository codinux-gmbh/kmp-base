package net.codinux.kotlin.text

import java.text.DecimalFormat
import java.text.NumberFormat

actual class CurrencyFormat(private val currencyFormat: DecimalFormat) {

    actual companion object {
        actual fun getForLocale(locale: Locale): CurrencyFormat? =
            LocalePlatform.javaLocaleFromLanguageTag(locale.languageTag)?.let { javaLocale ->
                (NumberFormat.getCurrencyInstance(javaLocale) as? DecimalFormat)?.let {
                    CurrencyFormat(it)
                }
            }
    }

    actual fun format(currencyValue: Double): String? =
        currencyFormat.format(currencyValue)
            .replace("SFr.", "CHF")

}