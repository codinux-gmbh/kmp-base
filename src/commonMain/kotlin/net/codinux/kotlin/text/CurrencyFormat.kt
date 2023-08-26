package net.codinux.kotlin.text

expect class CurrencyFormat {

    companion object {
        fun getForLocale(locale: Locale, useIsoCode: Boolean = false): CurrencyFormat?
    }

    fun format(currencyValue: Double): String?

}