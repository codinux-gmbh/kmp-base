package net.codinux.kotlin.text

import net.codinux.kotlin.util.Locale

actual class CurrencyFormat {

    actual companion object {

        actual fun getForLocale(locale: Locale): CurrencyFormat? {
            // here's an example how to format a NSNumber as currency:
            // let formatter = NumberFormatter()
            // formatter.numberStyle = .currency
            // // formatter.locale = NSLocale.current
            // formatter.string(from: price) // "$123.44"

            // for all options see: https://developer.apple.com/documentation/foundation/numberformatter

            return null
        }

    }

    actual fun format(currencyValue: Double): String? = null

}