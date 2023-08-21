package net.codinux.kotlin.text

import net.codinux.kotlin.util.Locale

expect class CurrencyFormat {

    companion object {
        fun getForLocale(locale: Locale): CurrencyFormat?
    }

    fun format(currencyValue: Double): String?

}