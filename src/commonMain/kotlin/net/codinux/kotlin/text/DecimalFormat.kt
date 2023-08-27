package net.codinux.kotlin.text

expect class DecimalFormat {

    companion object {
        fun getForLocale(locale: Locale, format: NumberFormat? = null): DecimalFormat?
    }

    fun format(value: Double): String?

}