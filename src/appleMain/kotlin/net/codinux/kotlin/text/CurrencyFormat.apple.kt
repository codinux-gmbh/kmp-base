@file:OptIn(UnsafeNumber::class)

package net.codinux.kotlin.text

import kotlinx.cinterop.UnsafeNumber
import platform.Foundation.*

actual class CurrencyFormat(private val formatter: NSNumberFormatter) {

    actual companion object {

        actual fun getForLocale(locale: Locale): CurrencyFormat? {
            val nsLocale = net.codinux.kotlin.platform.foundation.Locale.nsLocaleFromLanguageTag(locale.languageTag)
                ?: net.codinux.kotlin.platform.foundation.Locale.nsLocaleFromLanguageTag("${locale.language}_${locale.country}")

            return nsLocale?.let {
                CurrencyFormat(NSNumberFormatter().apply {
                    this.numberStyle = NSNumberFormatterCurrencyStyle
                    this.locale = nsLocale
                })
            }
        }

    }

    actual fun format(currencyValue: Double): String? =
        formatter.stringFromNumber(NSNumber.numberWithDouble(currencyValue))
            ?.replace(' ', ' ') // Apple system uses non-breaking space as white space separator -> normalize to make equal to other platforms

}