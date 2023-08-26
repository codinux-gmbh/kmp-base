@file:OptIn(UnsafeNumber::class)

package net.codinux.kotlin.text

import kotlinx.cinterop.UnsafeNumber
import platform.Foundation.*

actual class CurrencyFormat(private val formatter: NSNumberFormatter) {

    actual companion object {

        actual fun getForLocale(locale: Locale, useIsoCode: Boolean): CurrencyFormat? =
            net.codinux.kotlin.platform.foundation.Locale.nsFormatterForLocale(locale, if (useIsoCode) NSNumberFormatterCurrencyISOCodeStyle else NSNumberFormatterCurrencyStyle)?.let { formatter ->
                CurrencyFormat(formatter)
            }

    }

    actual fun format(currencyValue: Double): String? =
        formatter.stringFromNumber(NSNumber.numberWithDouble(currencyValue))
            ?.replace(' ', ' ') // Apple system uses non-breaking space as white space separator -> normalize to make equal to other platforms

}