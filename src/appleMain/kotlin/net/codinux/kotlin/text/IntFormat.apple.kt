@file:OptIn(UnsafeNumber::class)

package net.codinux.kotlin.text

import kotlinx.cinterop.UnsafeNumber
import platform.Foundation.*

actual class IntFormat(private val formatter: NSNumberFormatter) {

    actual companion object {

        actual fun getForLocale(locale: Locale): IntFormat? =
            net.codinux.kotlin.platform.foundation.Locale.nsFormatterForLocale(locale, NSNumberFormatterNoStyle)?.let { formatter ->
                IntFormat(formatter.apply {
                    this.generatesDecimalNumbers = false
                })
            }

    }

    actual fun format(value: Int): String? =
        formatter.stringFromNumber(NSNumber.numberWithInt(value))

}