package net.codinux.kotlin.text

import kotlinx.cinterop.cstr
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import kotlinx.cinterop.value
import net.codinux.kotlin.platform.posix.toChar
import net.codinux.kotlin.util.Locale
import platform.posix.LC_ALL
import platform.posix.LC_MONETARY

actual class CurrencyFormat {

    actual companion object {

        actual fun getForLocale(locale: Locale): CurrencyFormat? {
            // TODO: may implement some day:

            // there's a POSIX method strfmon() to format currencies, but it's not included in Kotlin Native. For its usage see:
            // https://stackoverflow.com/a/57022809

            // may see also nl_langinfo, but it's not included in Kotlin Native: https://man7.org/linux/man-pages/man3/nl_langinfo.3p.html

            // tried to set locale for which we like to retrieve information (currency symbol etc.), but didn't work
            val setLocaleResult = platform.posix.setlocale(LC_MONETARY, "en_US.UTF-8")
            val setLocaleResult2 = platform.posix.setlocale(LC_ALL, "en_US.UTF-8")
            val monetaryLocale = net.codinux.kotlin.platform.posix.Locale.getValueForCategory(LC_MONETARY)

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
            val positiveValueSpaceSeparator = localeConv.p_sep_by_space
            val positiveValueSpaceSeparator2 = localeConv.p_sep_by_space.toInt()
            val positiveValueSpaceSeparator3 = localeConv.p_sep_by_space.toInt().toChar()
            val positiveValueSpaceSeparator4 = localeConv.p_sep_by_space.toString()
            println("$monetaryLocale (${setLocaleResult?.toKString()}, ${setLocaleResult2?.toKString()}): $positiveValueSpaceSeparator / $positiveValueSpaceSeparator2 / $positiveValueSpaceSeparator3 / $positiveValueSpaceSeparator4")

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

            return null
        }
    }

    actual fun format(currencyValue: Double): String? = null

}