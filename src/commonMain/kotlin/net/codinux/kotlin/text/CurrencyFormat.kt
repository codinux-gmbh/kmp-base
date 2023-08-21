package net.codinux.kotlin.text

expect class CurrencyFormat {

    companion object {
        fun getForLocale(locale: Locale): CurrencyFormat?
    }

    fun format(currencyValue: Double): String?

}