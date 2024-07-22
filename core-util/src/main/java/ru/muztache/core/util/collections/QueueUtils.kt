package ru.muztache.core.util.collections

import java.util.LinkedList
import java.util.Queue

fun <I, O> Queue<I>.queueMap(block: (I) -> O): Queue<O> {
    val buffer: Queue<O> = LinkedList()
    forEach { element ->
        buffer.offer(block(element))
    }
    return buffer
}