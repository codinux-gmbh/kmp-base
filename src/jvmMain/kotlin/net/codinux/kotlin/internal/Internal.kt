package net.codinux.kotlin.internal

import net.codinux.kotlin.util.Locale

actual class Internal {

    actual companion object {

        actual fun getSystemLocale(): Locale {
            val jvmLocale = java.util.Locale.getDefault()

            return Locale(jvmLocale.language, jvmLocale.country, jvmLocale.variant.takeIf { it.isNotBlank() }, jvmLocale.script.takeIf { it.isNotBlank() })
        }

    }

}