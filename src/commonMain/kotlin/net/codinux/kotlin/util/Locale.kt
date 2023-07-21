package net.codinux.kotlin.util

import net.codinux.kotlin.internal.Internal

class Locale(
    val language: String,
    val country: String,
    // on JVM these have been the only non-empty variant values that i have found:
    // - Japanese (Japan,JP): JP
    // - Norwegian (Norway,Nynorsk): NY
    // - Thai (Thailand,TH): TH
    val variant: String? = null,
    // on JVM these have been the only non-empty script values that i have found:
    // - Serbian (Latin): Latn
    // - Serbian (Latin,Bosnia & Herzegovina): Latn
    // - Serbian (Latin,Montenegro): Latn
    // - Serbian (Latin,Serbia): Latn
    val script: String? = null
) {

    companion object {

        fun getDefault() = Internal.getSystemLocale()

    }

    override fun toString(): String {
        return "$language-$country"
    }

}