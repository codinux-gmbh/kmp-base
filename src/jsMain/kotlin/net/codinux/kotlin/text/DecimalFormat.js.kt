package net.codinux.kotlin.text

import net.codinux.kotlin.util.IntlNumberFormat

actual class DecimalFormat(private val decimalFormat: IntlNumberFormat) {

    actual companion object {

        actual fun getForLocale(locale: Locale) =
            LocalePlatform.getIntlNumberFormatForLocale(locale, "style: 'decimal'")
                ?.let { DecimalFormat(it) }

    }


    actual fun format(value: Double): String? {
        return decimalFormat.format(value)
    }

}