package net.codinux.kotlin.internal

import net.codinux.kotlin.text.Locale

expect class LocalePlatform {

    companion object {

        val AvailableLocales: List<Locale>

        fun getSystemLocale(): Locale

    }

}