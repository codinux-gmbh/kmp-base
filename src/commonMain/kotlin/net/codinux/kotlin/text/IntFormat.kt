package net.codinux.kotlin.text

expect class IntFormat {

    companion object {
        fun getForLocale(locale: Locale): IntFormat?
    }

    fun format(value: Int): String?

}