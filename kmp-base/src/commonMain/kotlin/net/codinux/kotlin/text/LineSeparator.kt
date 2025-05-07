package net.codinux.kotlin.text

import net.codinux.kotlin.Platform

object LineSeparator {

    const val Unix = "\n"

    const val Windows = "\r\n"

    const val MacOs = Unix

    const val OldMacOs = "\r"

    val System = Platform.lineSeparator

}