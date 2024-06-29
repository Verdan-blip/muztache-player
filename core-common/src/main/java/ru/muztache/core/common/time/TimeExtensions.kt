package ru.muztache.core.common.time

fun Long.toMmSsString(separator: String = ":"): String {
    val minutes = this / (1_000 * 60)
    if (minutes > 59)
        throw IllegalStateException("Duration is more than a hour")
    val seconds = this % (1_000 * 60)
    return "%.2d$separator%.2d".format(minutes, seconds)
}