package net.codinux.kotlin.platform

import kotlin.test.Test
import kotlin.test.assertTrue

class PlatformEnvironmentTest {

    @Test
    fun isMinified() {
        val result = PlatformEnvironment.isMinified

        assertTrue(result)
    }

}