package net.codinux.kotlin.lang

import kotlin.test.Test
import kotlin.test.assertEquals

// Copied from korlibs/korge: https://github.com/korlibs/korge/blob/main/kmem/src/commonTest/kotlin/korlibs/memory/ByteArrayBuilderTest.kt

class ByteArrayBuilderTest {

	@OptIn(ExperimentalStdlibApi::class)
	@Test
	fun name() {
		val bb = ByteArrayBuilder()
		bb.append(byteArrayOf(1))
		bb.append(byteArrayOf(2, 3))
		bb.append(4)
		bb.append(byteArrayOf(5))
		assertEquals("0102030405", bb.toByteArray().toHexString())
	}

}