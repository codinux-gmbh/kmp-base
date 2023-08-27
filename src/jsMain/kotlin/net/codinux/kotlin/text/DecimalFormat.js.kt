package net.codinux.kotlin.text

import net.codinux.kotlin.util.IntlNumberFormat

actual class DecimalFormat(private val decimalFormat: IntlNumberFormat) {

    actual companion object {

        actual fun getForLocale(locale: Locale, format: NumberFormat?) =
            LocalePlatform.getIntlNumberFormatForLocale(locale, "style: 'decimal'${toIntlNumberFormatOptions(format)}")
                ?.let { DecimalFormat(it) }

        private fun toIntlNumberFormatOptions(format: NumberFormat?): String {
            var options = ""

            if (format != null) {
                format.minimumIntegerDigits?.let { options += ", minimumIntegerDigits: $it" }
                // maximumIntegerDigits is not supported on JS
                format.maximumIntegerDigits?.let { println("maximumIntegerDigits is not supported on JS") }

                format.minimumFractionDigits?.let { options += ", minimumFractionDigits: $it" }
                format.maximumFractionDigits?.let { options += ", maximumFractionDigits: $it" }

                format.useGrouping?.let { options += ", useGrouping: $it" }
            }

            return options
        }

    }


    actual fun format(value: Double): String? {
        return decimalFormat.format(value)
    }

}