package ru.muztache.feature.player.presentation.mvi

sealed interface PlayerAction {

    data object Next : PlayerAction

    data object Previous : PlayerAction

    data object PlayPause : PlayerAction

    data class Seeking(val progress: Float) : PlayerAction

    data object SeekingFinished : PlayerAction
}