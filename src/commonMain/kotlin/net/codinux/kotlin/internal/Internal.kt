package net.codinux.kotlin.internal

import net.codinux.kotlin.util.Locale

expect class Internal {

    companion object {

        fun getSystemLocale(): Locale

    }

}