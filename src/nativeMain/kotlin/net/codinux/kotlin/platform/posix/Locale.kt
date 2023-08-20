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
        // the format is language[_territory][.codeset][@modifier], where
        // - language is an ISO 639 language code,
        // - territory is an ISO 3166 country code,
        // - codeset is a character set or encoding identifier like ISO-8859-1 or UTF-8.
        //   For a list of all supported locales, try "locale -a" (see locale(1)).

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

        val countryCode = if (indexOfUnderscore > -1) {
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

        val languageCode = if (indexOfUnderscore > -1) {
            locale.substring(0, indexOfUnderscore)
        } else if (indexOfDot > -1) {
            locale.substring(0, indexOfDot)
        } else if (indexOfAt > -1) {
            locale.substring(0, indexOfAt)
        } else {
            locale
        }

        return Locale(languageCode, countryCode, variant = variant)
    }

    private fun getValueForCategory(category: Int): String? {
        val localePointer = platform.posix.setlocale(category, "")
        val locale = localePointer?.toKString()

        // LC_ALL value sometimes contains all LC_xyz values which are separated by ';' -> filter out these
        return if (locale.isNullOrBlank() || locale == "C" || (category == LC_ALL || locale.contains(';'))) {
            null
        } else {
            locale
        }
    }

    // available locales may can be read from /usr/share/i18n/SUPPORTED, but that's of course not an robust and universe way to get all locales
    // "locale -a" didn't work an my system
    // This is may due to not all locales have been generated, see "cat /etc/locale.gen" for available and currently unavailable locales

    private fun formatCurrency() {
        // there's a POSIX method strfmon() to format currencies, but it's not included in Kotlin Native. For its usage see:
        // https://stackoverflow.com/a/57022809

        // may see also nl_langinfo, but it's not included in Kotlin Native: https://man7.org/linux/man-pages/man3/nl_langinfo.3p.html

        val localeConvPointer = platform.posix.localeconv()
        val localeConv = localeConvPointer!!.pointed

        val decimalSeparator = localeConv.decimal_point.toChar()
        val thousandsSeparator = localeConv.thousands_sep.toChar()
        val grouping = localeConv.grouping?.toKString()
        val grouping2 = localeConv.grouping?.pointed?.value
        val grouping3 = localeConv.grouping?.pointed?.value.toString()

        val currencySymbol = localeConv.currency_symbol?.toKString()
        val currencyCode = localeConv.int_curr_symbol?.toKString()
        val currencyFractionDigits = localeConv.frac_digits.toInt()
        val currencyGrouping = localeConv.mon_grouping?.pointed?.value.toString()
        val currencyDecimalSeparator = localeConv.mon_decimal_point.toChar()
        val currencyThousandsSeparator = localeConv.mon_thousands_sep.toChar()

        val positiveSign = localeConv.positive_sign

        // there are also fields to determine the positive and negative sign, if there's a space between number and currency symbol,
        // if the currency symbol preceeds the formatted monetary quantity, ... See e.g.
        // https://man7.org/linux/man-pages/man3/localeconv.3p.html
        // https://www.ibm.com/docs/en/i/7.2?topic=functions-localeconv-retrieve-information-from-environment

        println("Currency: $currencySymbol / $currencyCode, decimal: $decimalSeparator, thousands: $thousandsSeparator, frac_digits: $currencyFractionDigits, grouping: $grouping / $grouping2 / $grouping3")
        println()

//        return FormatSymbols(
//            decimalSeparator,
//            currencySymbol,
//            currencyFractionDigits,
//            currencyFractionDigits,
//            null,
//            localeConv.int_frac_digits.toInt()
//        )
    }
}