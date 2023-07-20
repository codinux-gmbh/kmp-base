package net.codinux.kotlin.concurrent

import java.util.concurrent.CopyOnWriteArraySet

actual open class ConcurrentSet<E> : CopyOnWriteArraySet<E>(), Set<E>