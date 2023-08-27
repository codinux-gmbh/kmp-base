package net.codinux.kotlin.text

actual class PercentFormat {

    actual companion object {

        actual fun getForLocale(locale: Locale): PercentFormat? = null

    }

    actual fun format(value: Double): String? = null

}