@file:OptIn(UnsafeNumber::class)

package net.codinux.kotlin.text

import kotlinx.cinterop.UnsafeNumber
import net.codinux.kotlin.platform.foundation.toNSNumber
import platform.Foundation.*

actual class PercentFormat(private val formatter: NSNumberFormatter) {

    actual companion object {

        actual fun getForLocale(locale: Locale): PercentFormat? =
            net.codinux.kotlin.platform.foundation.Locale.nsFormatterForLocale(locale, NSNumberFormatterPercentStyle)?.let { formatter ->
                PercentFormat(formatter)
            }

    }

    actual fun format(value: Double): String? =
        formatter.stringFromNumber(value.toNSNumber())
            ?.replace(' ', ' ') // Apple system uses non-breaking space as white space separator -> normalize to make equal to other platforms

}