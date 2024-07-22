package ru.muztache.core.util.time

class InvalidProgressFormatException(
    value: Int
) : Exception("Progress must be in range: 0 <= x <= 100. Your value is: $value")

@JvmInline
value class Progress(val value: Int) {

    init {
        if (value < 0 || value > 100) throw InvalidProgressFormatException(value)
    }

    override fun toString(): String = value.toString()
}

fun Progress.toMilliseconds(duration: Milliseconds): Milliseconds =
    Milliseconds((value / 100f * duration.value).toLong())

fun Int.toProgress(): Progress = Progress(value = this)