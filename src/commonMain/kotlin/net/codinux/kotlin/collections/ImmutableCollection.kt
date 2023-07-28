package net.codinux.kotlin.collections

class ImmutableCollection<E>(private val wrapped: Collection<E>) : Collection<E> {

    constructor(vararg elements: E) : this(elements.asList())


    override val size = wrapped.size

    override fun isEmpty() = wrapped.isEmpty()

    override fun contains(element: E) = wrapped.contains(element)

    override fun containsAll(elements: Collection<E>) = wrapped.containsAll(elements)

    override fun iterator() = wrapped.iterator()

    override fun toString() = wrapped.toString()

}