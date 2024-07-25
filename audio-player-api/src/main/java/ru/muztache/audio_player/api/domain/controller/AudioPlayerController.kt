package ru.muztache.audio_player.api.domain.controller

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.core.util.time.Milliseconds

interface AudioPlayerController {
    
    val playerState: StateFlow<AudioPlayerState>

    val playerEvent: SharedFlow<AudioPlayerEvent>

    fun release()

    fun play()

    fun pause()

    fun seekTo(millis: Milliseconds)

    fun play(audioItem: AudioItem)

    fun play(audioItems: List<AudioItem>)

    fun skipToNext()

    fun playNext(audioItem: AudioItem)

    fun playNext(audioItems: List<AudioItem>)

    fun skipToPrevious()

    fun clearQueue()
}