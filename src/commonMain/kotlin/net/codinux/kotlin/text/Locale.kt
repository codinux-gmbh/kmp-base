package net.codinux.kotlin.text

import net.codinux.kotlin.internal.Internal

class Locale(
    val language: String,
    val country: String,
    // on JVM these have been the only non-empty script values that i have found:
    // - Serbian (Latin): Latn
    // - Serbian (Latin,Bosnia & Herzegovina): Latn
    // - Serbian (Latin,Montenegro): Latn
    // - Serbian (Latin,Serbia): Latn
    val script: String? = null,
    // on JVM these have been the only non-empty variant values that i have found:
    // - Japanese (Japan,JP): JP
    // - Norwegian (Norway,Nynorsk): NY
    // - Thai (Thailand,TH): TH
    val variant: String? = null
) {

    companion object {

        val AvailableLocales by lazy { Internal.AvailableLocales }

        fun getDefault() = Internal.getSystemLocale()

        fun languageTagFor(locale: Locale) = languageTagFor(locale.language, locale.country)

        fun languageTagFor(language: String, country: String) = "$language-$country"

    }

    val languageTag by lazy { languageTagFor(this) }

    override fun toString(): String {
        return "$language-$country"
    }

}