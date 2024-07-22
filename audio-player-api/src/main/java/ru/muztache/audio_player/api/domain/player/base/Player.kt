package ru.muztache.audio_player.api.domain.player.base

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.player.AudioPlayerEvent
import ru.muztache.audio_player.api.domain.player.AudioPlayerState
import ru.muztache.core.util.time.Milliseconds

interface Player {

    val playerState: StateFlow<AudioPlayerState>

    val playerEvent: SharedFlow<AudioPlayerEvent>

    suspend fun start()

    suspend fun stop()

    suspend fun resume()

    suspend fun pause()

    suspend fun seekTo(millis: Milliseconds)

    suspend fun play(audioItem: AudioItem)

    suspend fun play(audioItems: List<AudioItem>)

    suspend fun skipToNext()

    suspend fun playNext(audioItem: AudioItem)

    suspend fun playNext(audioItems: List<AudioItem>)

    suspend fun skipToPrevious()

    suspend fun clearQueue()
}