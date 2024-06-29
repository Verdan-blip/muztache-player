package ru.muztache.core.common.event

sealed interface MusicEvent {

    data class ProgressChanged(val progress: Long) : MusicEvent

    data class ItemDurationChanged(val duration: Long) : MusicEvent

    data class IsPlayingChanged(val isPlaying: Boolean) : MusicEvent

    data class PlaybackError(val message: String) : MusicEvent

}