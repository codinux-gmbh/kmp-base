package net.codinux.kotlin

import io.kotest.matchers.equals.shouldNotBeEqual
import kotlin.test.Test

class PlatformTest {

    @Test
    fun platformType() {
        val result = Platform.type

        result.shouldNotBeEqual(PlatformType.Unknown)
    }

}