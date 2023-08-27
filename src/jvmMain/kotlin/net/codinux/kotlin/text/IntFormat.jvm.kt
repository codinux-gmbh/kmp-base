package net.codinux.kotlin.text

import java.text.NumberFormat

actual class IntFormat(private val intFormat: NumberFormat) {

    actual companion object {

        actual fun getForLocale(locale: Locale): IntFormat? =
            LocalePlatform.javaLocaleFromLanguageTag(locale.languageTag)?.let { javaLocale ->
                NumberFormat.getIntegerInstance(javaLocale)?.let { numberFormat ->
                    IntFormat(numberFormat)
                }
            }
    }

    actual fun format(value: Int): String? =
        intFormat.format(value)

}