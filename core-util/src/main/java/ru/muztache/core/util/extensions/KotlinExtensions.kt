package ru.muztache.core.util.extensions

fun Int.between(left: Int, right: Int): Boolean = this > left && this < right

fun Int.between(left: Float, right: Float): Boolean = this > left && this < right