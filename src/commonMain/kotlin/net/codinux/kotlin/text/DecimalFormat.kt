package net.codinux.kotlin.text

expect class DecimalFormat {

    companion object {
        fun getForLocale(locale: Locale): DecimalFormat?
    }

    fun format(value: Double): String?

}