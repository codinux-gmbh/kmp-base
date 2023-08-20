package net.codinux.kotlin.internal

import net.codinux.kotlin.util.Locale

expect class Internal {

    companion object {

        val AvailableLocales: List<Locale>

        fun getSystemLocale(): Locale

    }

}