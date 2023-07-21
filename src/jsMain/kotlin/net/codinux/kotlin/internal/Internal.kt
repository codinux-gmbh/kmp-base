package net.codinux.kotlin.internal

import net.codinux.kotlin.util.IntlLocale
import net.codinux.kotlin.util.Locale

actual class Internal {

    actual companion object {

        actual fun getSystemLocale(): Locale {
            val localeString = js("Intl.NumberFormat().resolvedOptions().locale")
            val locale = eval("new Intl.Locale(\"$localeString\")").unsafeCast<IntlLocale>()

            return Locale(locale.language, locale.region, null, locale.script)
        }

    }

}