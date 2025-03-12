package net.codinux.kotlin.text

import kotlin.test.Test
import kotlin.test.assertEquals

// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/korio/src/commonTest/kotlin/korlibs/io/lang/SingleByteCharsetTest.kt

class SingleByteCharsetTest {

	@Test
	fun test() {
		val charset = SingleByteCharset("demo", (0 until 256).map { (it + 0x1000).toChar() })
		assertEquals("\u1000\u1001\u1002\u1003", byteArrayOf(0, 1, 2, 3).toString(charset))
		assertEquals(byteArrayOf(0, 1, 2, 3).toList(), "\u1000\u1001\u1002\u1003".toByteArray(charset).toList())
		assertEquals(byteArrayOf('?'.code.toByte(), '?'.code.toByte()).toList(), "\u4000\u4001".toByteArray(charset).toList())
	}

}