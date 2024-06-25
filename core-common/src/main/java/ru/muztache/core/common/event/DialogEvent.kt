package ru.muztache.core.common.event

sealed class DialogEvent(val message: String) {

    class Error(message: String) : DialogEvent(message)

    class Informational(message: String) : DialogEvent(message)

    class Success(message: String) : DialogEvent(message)

    class Special(val title: String, message: String) : DialogEvent(message)
}