package net.codinux.kotlin.text

expect class PercentFormat {

    companion object {
        fun getForLocale(locale: Locale): PercentFormat?
    }

    fun format(value: Double): String?

}