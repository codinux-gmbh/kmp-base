@file:OptIn(UnsafeNumber::class)

package net.codinux.kotlin.text

import kotlinx.cinterop.UnsafeNumber
import net.codinux.kotlin.platform.foundation.toNSUInteger
import platform.Foundation.*

actual class DecimalFormat(private val formatter: NSNumberFormatter) {

    actual companion object {

        actual fun getForLocale(locale: Locale, format: NumberFormat?): DecimalFormat? =
            net.codinux.kotlin.platform.foundation.Locale.nsFormatterForLocale(locale, NSNumberFormatterDecimalStyle)?.let { formatter ->
                DecimalFormat(applyFormat(formatter, format))
            }

        private fun applyFormat(formatter: NSNumberFormatter, format: NumberFormat?): NSNumberFormatter {
            if (format != null) {
                format.minimumIntegerDigits?.let { formatter.minimumIntegerDigits = it.toNSUInteger() }
                format.maximumIntegerDigits?.let { formatter.maximumIntegerDigits = it.toNSUInteger() }

                format.minimumFractionDigits?.let { formatter.minimumFractionDigits = it.toNSUInteger() }
                format.maximumFractionDigits?.let { formatter.maximumFractionDigits = it.toNSUInteger() }

                format.useGrouping?.let { formatter.usesGroupingSeparator = it }
            }

            return formatter
        }

    }

    actual fun format(value: Double): String? =
        formatter.stringFromNumber(NSNumber.numberWithDouble(value))

}