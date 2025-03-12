package net.codinux.kotlin.text

import kotlin.test.Test
import kotlin.test.assertEquals

// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/korio/src/commonTest/kotlin/korlibs/io/lang/UTF8Test.kt

@OptIn(ExperimentalStdlibApi::class)
class UTF8Test {

	@Test
	fun test() {
		assertEquals(byteArrayOf('h'.code.toByte(), 'e'.code.toByte(), 'l'.code.toByte(), 'l'.code.toByte(), 'o'.code.toByte()).toHexString(), "hello".toByteArray(Charsets.UTF8).toHexString())
		assertEquals("hello", byteArrayOf('h'.code.toByte(), 'e'.code.toByte(), 'l'.code.toByte(), 'l'.code.toByte(), 'o'.code.toByte()).toString(Charsets.UTF8))
	}

}