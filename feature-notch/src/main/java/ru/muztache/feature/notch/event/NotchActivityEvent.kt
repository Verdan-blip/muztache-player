package ru.muztache.feature.notch.event

sealed interface NotchActivityEvent {

    data object RequestPermissionForOverlaying : NotchActivityEvent
}