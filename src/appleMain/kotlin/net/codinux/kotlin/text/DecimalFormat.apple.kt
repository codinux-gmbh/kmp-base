@file:OptIn(UnsafeNumber::class)

package net.codinux.kotlin.text

import kotlinx.cinterop.UnsafeNumber
import platform.Foundation.*

actual class DecimalFormat(private val formatter: NSNumberFormatter) {

    actual companion object {

        actual fun getForLocale(locale: Locale): DecimalFormat? =
            net.codinux.kotlin.platform.foundation.Locale.nsFormatterForLocale(locale, NSNumberFormatterDecimalStyle)?.let { formatter ->
                DecimalFormat(formatter)
            }

    }

    actual fun format(value: Double): String? =
        formatter.stringFromNumber(NSNumber.numberWithDouble(value))

}