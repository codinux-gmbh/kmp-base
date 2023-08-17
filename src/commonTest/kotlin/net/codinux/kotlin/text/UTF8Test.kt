package net.codinux.kotlin.text

import net.codinux.kotlin.encoding.hex
import kotlin.test.Test
import kotlin.test.assertEquals

// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/korio/src/commonTest/kotlin/korlibs/io/lang/UTF8Test.kt

class UTF8Test {

	@Test
	fun test() {
		assertEquals(byteArrayOf('h'.code.toByte(), 'e'.code.toByte(), 'l'.code.toByte(), 'l'.code.toByte(), 'o'.code.toByte()).hex, "hello".toByteArray(Charsets.UTF8).hex)
		assertEquals("hello", byteArrayOf('h'.code.toByte(), 'e'.code.toByte(), 'l'.code.toByte(), 'l'.code.toByte(), 'o'.code.toByte()).toString(Charsets.UTF8))
	}

}