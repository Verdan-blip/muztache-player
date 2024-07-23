package ru.muztache.audio_player.impl.presentation.service.binder

import android.os.Binder
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.player.AudioPlayerEvent
import ru.muztache.audio_player.api.domain.player.AudioPlayerState
import ru.muztache.audio_player.api.domain.player.controller.AudioPlayerController
import ru.muztache.audio_player.impl.presentation.service.AudioPlayerService
import ru.muztache.core.util.time.Milliseconds

class AudioPlayerServiceBinder(
    private val service: AudioPlayerService
) : Binder(), AudioPlayerController {

    override val playerState: StateFlow<AudioPlayerState>
        get() = service.playerState

    override val playerEvent: SharedFlow<AudioPlayerEvent>
        get() = service.playerEvent

    override suspend fun start() {
        service.start()
    }

    override suspend fun stop() {
        service.stop()
    }

    override suspend fun resume() {
        service.resume()
    }

    override suspend fun pause() {
        service.pause()
    }

    override suspend fun seekTo(millis: Milliseconds) {
        service.seekTo(millis)
    }

    override suspend fun play(audioItem: AudioItem) {
        service.play(audioItem)
    }

    override suspend fun play(audioItems: List<AudioItem>) {
        service.play(audioItems)
    }

    override suspend fun skipToNext() {
        service.skipToNext()
    }

    override suspend fun playNext(audioItem: AudioItem) {
        service.playNext(audioItem)
    }

    override suspend fun playNext(audioItems: List<AudioItem>) {
        service.playNext(audioItems)
    }

    override suspend fun skipToPrevious() {
        service.skipToPrevious()
    }

    override suspend fun clearQueue() {
        service.clearQueue()
    }
}