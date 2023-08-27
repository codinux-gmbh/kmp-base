package net.codinux.kotlin.text

import net.codinux.kotlin.util.IntlNumberFormat

actual class IntFormat(private val intFormat: IntlNumberFormat) {

    actual companion object {

        actual fun getForLocale(locale: Locale): IntFormat? =
            LocalePlatform.getIntlNumberFormatForLocale(locale, "style: 'decimal', maximumFractionDigits: 0")
                ?.let { IntFormat(it) }

    }


    actual fun format(value: Int): String? {
        return intFormat.format(value)
    }

}