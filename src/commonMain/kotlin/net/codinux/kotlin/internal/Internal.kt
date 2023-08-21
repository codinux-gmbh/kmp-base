package net.codinux.kotlin.internal

import net.codinux.kotlin.text.Locale

expect class Internal {

    companion object {

        val AvailableLocales: List<Locale>

        fun getSystemLocale(): Locale

    }

}