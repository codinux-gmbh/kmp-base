package net.codinux.kotlin.text

expect class LocalePlatform {

    companion object {

        val AvailableLocales: List<Locale>

        fun getSystemLocale(): Locale

    }

}