package net.codinux.kotlin.text

import java.text.NumberFormat

actual class DecimalFormat(private val decimalFormat: NumberFormat) {

    actual companion object {

        actual fun getForLocale(locale: Locale, format: net.codinux.kotlin.text.NumberFormat?): DecimalFormat? =
            LocalePlatform.javaLocaleFromLanguageTag(locale.languageTag)?.let { javaLocale ->
                NumberFormat.getInstance(javaLocale)?.let { numberFormat ->
                    DecimalFormat(applyFormat(numberFormat, format))
                }
            }

        private fun applyFormat(numberFormat: NumberFormat, format: net.codinux.kotlin.text.NumberFormat?): NumberFormat {
            if (format == null) {
                return numberFormat
            }

            format.minimumIntegerDigits?.let { numberFormat.minimumIntegerDigits = it }
            format.maximumIntegerDigits?.let { numberFormat.maximumIntegerDigits = it }

            format.minimumFractionDigits?.let { numberFormat.minimumFractionDigits = it }
            format.maximumFractionDigits?.let { numberFormat.maximumFractionDigits = it }

            format.useGrouping?.let { numberFormat.isGroupingUsed = it }

            return numberFormat
        }
    }

    actual fun format(value: Double): String? =
        decimalFormat.format(value)

}