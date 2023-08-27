package net.codinux.kotlin.text

import java.text.NumberFormat

actual class DecimalFormat(private val decimalFormat: NumberFormat) {

    actual companion object {

        actual fun getForLocale(locale: Locale): DecimalFormat? =
            LocalePlatform.javaLocaleFromLanguageTag(locale.languageTag)?.let { javaLocale ->
                NumberFormat.getInstance(javaLocale)?.let { numberFormat ->
                    DecimalFormat(numberFormat)
                }
            }
    }

    actual fun format(value: Double): String? =
        decimalFormat.format(value)

}