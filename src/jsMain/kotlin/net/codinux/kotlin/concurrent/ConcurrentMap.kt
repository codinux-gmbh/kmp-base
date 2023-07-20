package net.codinux.kotlin.concurrent

/**
 * JavaScript has only one thread, so no need to take care of any thread-safety structures
 */
actual open class ConcurrentMap<K, V> : LinkedHashMap<K, V>()