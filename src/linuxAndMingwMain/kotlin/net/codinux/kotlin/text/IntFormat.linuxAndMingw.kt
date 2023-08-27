package net.codinux.kotlin.text

actual class IntFormat {

    actual companion object {

        actual fun getForLocale(locale: Locale): IntFormat? = null

    }

    actual fun format(value: Int): String? = null

}