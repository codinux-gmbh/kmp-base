package net.codinux.kotlin.text

import java.text.NumberFormat

actual class PercentFormat(private val percentFormat: NumberFormat) {

    actual companion object {

        actual fun getForLocale(locale: Locale): PercentFormat? =
            LocalePlatform.javaLocaleFromLanguageTag(locale.languageTag)?.let { javaLocale ->
                NumberFormat.getPercentInstance(javaLocale)?.let { numberFormat ->
                    PercentFormat(numberFormat)
                }
            }
    }

    actual fun format(value: Double): String? =
        percentFormat.format(value)

}