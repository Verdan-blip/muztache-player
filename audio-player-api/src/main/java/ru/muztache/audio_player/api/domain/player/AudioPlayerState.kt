package ru.muztache.audio_player.api.domain.player

import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.entity.AudioItemInfo
import ru.muztache.core.util.time.Milliseconds

data class AudioPlayerState(
    val isActive: Boolean = false,
    val isPaused: Boolean = true,
    val currentPlayingAudioItem: AudioItemInfo? = null,
    val currentProgress: Milliseconds = Milliseconds(value = 0),
    val remainingAudioItems: List<AudioItem> = listOf()
)