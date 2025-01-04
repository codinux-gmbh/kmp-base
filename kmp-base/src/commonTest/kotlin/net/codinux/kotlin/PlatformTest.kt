package net.codinux.kotlin

import kotlin.test.Test

class PlatformTest {

    @Test
    fun printOs() {
        println("${Platform.type}: OS = ${Platform.osName}, ${Platform.osVersion}, CPU architecture = ${Platform.cpuArchitecture}")
    }

    @Test
    fun printLineSeparatorAndFileSeparator() {
        println("${Platform.type}: Line separator = ${Platform.lineSeparator.map { if (it == '\n') "\\n" else if (it == '\r') "\\r" else it.code.toString() }.joinToString("")}, " +
                "File separator = ${Platform.fileSeparator}")
    }

}