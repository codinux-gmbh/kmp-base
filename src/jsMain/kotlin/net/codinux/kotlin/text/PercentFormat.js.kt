package net.codinux.kotlin.text

import net.codinux.kotlin.util.IntlNumberFormat

actual class PercentFormat(private val percentFormat: IntlNumberFormat) {

    actual companion object {

        actual fun getForLocale(locale: Locale): PercentFormat? =
            LocalePlatform.getIntlNumberFormatForLocale(locale, "style: 'percent'")
                ?.let { PercentFormat(it) }

    }


    actual fun format(value: Double): String? {
        return percentFormat.format(value)
            ?.replace(' ', ' ') // JS uses non-breaking space as white space separator -> normalize to make equal to other platforms
    }

}