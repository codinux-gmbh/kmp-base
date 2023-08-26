package net.codinux.kotlin.text

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat

actual class CurrencyFormat(private val currencyFormat: NumberFormat) {

    actual companion object {

        actual fun getForLocale(locale: Locale, useIsoCode: Boolean): CurrencyFormat? =
            LocalePlatform.javaLocaleFromLanguageTag(locale.languageTag)?.let { javaLocale ->
                NumberFormat.getCurrencyInstance(javaLocale)?.let { currencyFormat ->
                    if (useIsoCode) {
                        (currencyFormat as? DecimalFormat)?.let {
                            currencyFormat.decimalFormatSymbols = DecimalFormatSymbols(javaLocale).apply {
                                this.currencySymbol = currencyFormat.currency.currencyCode
                            }
                        }
                    }

                    CurrencyFormat(currencyFormat)
                }
            }
    }

    actual fun format(currencyValue: Double): String? =
        currencyFormat.format(currencyValue)
            .replace("SFr.", "CHF")

}