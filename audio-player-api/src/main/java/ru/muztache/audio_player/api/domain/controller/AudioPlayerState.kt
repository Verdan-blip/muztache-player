package ru.muztache.audio_player.api.domain.controller

import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.entity.AudioItemInfo
import ru.muztache.core.util.time.Milliseconds

data class AudioPlayerState(
    val isActive: Boolean = false,
    val state: PlayingState = PlayingState.BUFFERING,
    val currentPlayingAudioItem: AudioItemInfo? = null,
    val currentPlayingIndex: Int = -1,
    val currentProgress: Milliseconds = Milliseconds(value = 0),
    val playingSpeed: Float = 1f,
    val remainingAudioItems: List<AudioItem> = listOf()
)