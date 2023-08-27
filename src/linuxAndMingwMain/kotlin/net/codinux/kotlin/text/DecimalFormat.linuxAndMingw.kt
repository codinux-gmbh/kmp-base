package net.codinux.kotlin.text

actual class DecimalFormat {

    actual companion object {

        actual fun getForLocale(locale: Locale, format: NumberFormat?): DecimalFormat? = null

    }

    actual fun format(value: Double): String? = null

}