package ru.muztache.core.util.time

fun Long.toMmSsString(separator: String = ":"): String {
    val minutes = this / (1_000 * 60)
    if (minutes > 59)
        throw IllegalStateException("Duration is more than a hour")
    val seconds = this % (1_000 * 60)
    return "%02d$separator%02d".format(minutes, seconds)
}