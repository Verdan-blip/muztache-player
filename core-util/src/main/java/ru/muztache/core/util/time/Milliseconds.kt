package ru.muztache.core.util.time

class IllegalMillisecondsFormatException(
    value: Long
) : Exception("Milliseconds must be non-negative. Your value is: $value")

@JvmInline
value class Milliseconds(
    val value: Long
) {

    init {
        if (value < 0) throw IllegalMillisecondsFormatException(value)
    }
}

fun Milliseconds.toProgress(duration: Milliseconds): Progress =
    Progress((value / duration.value.toFloat() * 100).toInt())

fun Milliseconds.toMmSsString(separator: String = ":"): String {
    val minutes = value / (1_000 * 60)
    if (minutes > 59)
        throw IllegalStateException("Duration is more than a hour")
    val seconds = value % (1_000 * 60)
    return "%.2d$separator%.2d".format(minutes, seconds)
}

fun Long.toMilliseconds(safe: Boolean = false): Milliseconds = Milliseconds(value = this)

fun Int.toMilliseconds(): Milliseconds = Milliseconds(value = toLong())