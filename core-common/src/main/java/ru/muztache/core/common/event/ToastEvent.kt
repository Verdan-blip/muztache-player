package ru.muztache.core.common.event

sealed class ToastEvent(val message: String) {

    class Error(message: String) : ToastEvent(message)

    class Success(message: String) : ToastEvent(message)

    class Informational(message: String) : ToastEvent(message)
}