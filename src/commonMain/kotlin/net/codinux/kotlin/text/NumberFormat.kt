package net.codinux.kotlin.text

data class NumberFormat(
    val minimumIntegerDigits: Int? = null,
    val maximumIntegerDigits: Int? = null, // not available on JS, has to be simulated there manually
    val minimumFractionDigits: Int? = null,
    val maximumFractionDigits: Int? = null,
//    val minimumSignificantDigits: Int? = null, // not available on JVM
//    val maximumSignificantDigits: Int? = null, // not available on JVM
    val useGrouping: Boolean? = null,
//    val alwaysShowSign: Boolean? = null, // auto, always, exceptZero, negative, never // not available on JVM
)