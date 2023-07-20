package net.codinux.kotlin.concurrent

import java.util.concurrent.ConcurrentHashMap

actual open class ConcurrentMap<K, V> : ConcurrentHashMap<K, V>()