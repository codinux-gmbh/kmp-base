package net.codinux.kotlin.platform.posix

import kotlinx.cinterop.*
import net.codinux.kotlin.util.Locale
import platform.posix.LC_ALL

// see header <locale.h> or <bits/locale.h> respectively (e.g. /user/include/locale.h)

const val LC_MESSAGES = 5

const val LC_PAPER = 7

const val LC_NAME = 8

const val LC_ADDRESS = 9

const val LC_TELEPHONE = 10

const val LC_MEASUREMENT = 11

const val LC_IDENTIFICATION = 12


object Locale {

    fun getSystemLocale(): Locale {
        // In Posix systems the locale is determined by these values in this order:
        // - the LANGUAGE environment value
        // - the LC_ALL locale value
        // - the LC_ALL locale value
        // - the LANGUAGE environment value
        val language = platform.posix.getenv("LANGUAGE")?.toKString()
            ?: getValueForCategory(LC_ALL)
            ?: getValueForCategory(LC_MESSAGES)
            ?: platform.posix.getenv("LANG")?.toKString()
            ?: "en_US" // fallback value

        return parseToLocale(language)
    }

    internal fun parseToLocale(locale: String): Locale {
        // the format is language_country.encoding@variant, encoding and variant are optional

        var indexOfUnderscore = locale.indexOf('_')
        if (indexOfUnderscore < 0) {
            indexOfUnderscore = locale.indexOf('-') // Apple systems use '-' instead of '_'
        }
        val indexOfDot = locale.indexOf('.')
        val indexOfAt = locale.indexOf('@')

        val variant = if (indexOfAt > -1) {
            locale.substring(indexOfAt + 1)
        } else {
            null
        }

        val country = if (indexOfUnderscore > -1) {
            if (indexOfDot > -1) {
                locale.substring(indexOfUnderscore + 1, indexOfDot)
            } else if (indexOfAt > -1) {
                locale.substring(indexOfUnderscore + 1, indexOfAt)
            } else {
                locale.substring(indexOfUnderscore + 1)
            }
        } else {
            ""
        }

        val language = if (indexOfUnderscore > -1) {
            locale.substring(0, indexOfUnderscore)
        } else if (indexOfDot > -1) {
            locale.substring(0, indexOfDot)
        } else if (indexOfAt > -1) {
            locale.substring(0, indexOfAt)
        } else {
            locale
        }

        return Locale(language, country, variant)
    }

    fun getValueForCategory(category: Int): String? {
        val localePointer = platform.posix.setlocale(category, "")
        val locale = localePointer?.toKString()

        // LC_ALL value sometimes contains all LC_xyz values which are separated by ';' -> filter out these
        return if (locale.isNullOrBlank() || locale == "C" || (category == LC_ALL || locale.contains(';'))) {
            null
        } else {
            locale
        }
    }
}