package net.codinux.kotlin.collections

class ImmutableSet<T>(private val wrapped: Set<T>) : Set<T> {

    override val size = wrapped.size

    override fun isEmpty() = wrapped.isEmpty()

    override fun contains(element: T) = wrapped.contains(element)

    override fun containsAll(elements: Collection<T>) = wrapped.containsAll(elements)

    override fun iterator() = wrapped.iterator()

}