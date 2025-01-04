package net.codinux.kotlin

import kotlin.test.Test

class PlatformTest {

    @Test
    fun printOs() {
        println("${Platform.type}: OS = ${Platform.osName}, ${Platform.osVersion}, CPU architecture = ${Platform.cpuArchitecture}")
    }

}