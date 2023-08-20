package net.codinux.kotlin.collections

fun <E> Collection<E>.containsNot(element: E) = this.contains(element) == false