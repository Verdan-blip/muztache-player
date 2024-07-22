package ru.muztache.audio_player.api.domain.player

import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.entity.AudioItemInfo
import ru.muztache.core.util.time.Milliseconds
import ru.muztache.core.util.time.Progress

sealed interface AudioPlayerEvent {

    data object Initialized : AudioPlayerEvent

    data object Started : AudioPlayerEvent

    data object Stopped : AudioPlayerEvent

    data class PlaybackError(val throwable: Throwable) : AudioPlayerEvent

    data class AudioItemChanged(val audioItemInfo: AudioItemInfo) : AudioPlayerEvent

    data class RemainingAudioItemsChanged(val audioItems: List<AudioItem>) : AudioPlayerEvent

    data class PlayingProgressChanged(val newProgress: Milliseconds) : AudioPlayerEvent

    data class BufferingProgressChanged(val newProgress: Progress) : AudioPlayerEvent

    data object Paused : AudioPlayerEvent

    data object Resumed : AudioPlayerEvent
}