package ru.muztache.audio_player.impl.presentation.service.binder

import android.os.Binder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.player.AudioPlayer
import ru.muztache.audio_player.api.domain.player.AudioPlayerEvent
import ru.muztache.audio_player.api.domain.player.AudioPlayerState
import ru.muztache.audio_player.impl.presentation.service.AudioPlayerService
import ru.muztache.core.common.coroutines.scopes.globalScope
import ru.muztache.core.util.time.Milliseconds

class AudioPlayerServiceBinder(
    private val playerService: AudioPlayerService
) : Binder(), AudioPlayer {

    private val _playerState = MutableStateFlow(AudioPlayerState())
    override val playerState: StateFlow<AudioPlayerState>
        get() = _playerState

    private val _playerEvent = MutableSharedFlow<AudioPlayerEvent>()
    override val playerEvent: SharedFlow<AudioPlayerEvent>
        get() = _playerEvent

    init {
        globalScope.launch(Dispatchers.IO) { collectPlayerServiceState() }
        globalScope.launch(Dispatchers.IO) { collectPlayerServiceEvent() }
    }

    override suspend fun start() {
        playerService.start()
    }

    override suspend fun stop() {
        playerService.stop()
    }

    override suspend fun resume() {
        playerService.resume()
    }

    override suspend fun pause() {
        playerService.pause()
    }

    override suspend fun play(audioItem: AudioItem) {
        playerService.play(audioItem)
    }

    override suspend fun play(audioItems: List<AudioItem>) {
        playerService.play(audioItems)
    }

    override suspend fun playNext(audioItem: AudioItem) {
        playerService.playNext(audioItem)
    }

    override suspend fun playNext(audioItems: List<AudioItem>) {
        playerService.playNext(audioItems)
    }

    override suspend fun clearQueue() {
        playerService.clearQueue()
    }

    override suspend fun seekTo(millis: Milliseconds) {
        playerService.seekTo(millis)
    }

    override suspend fun skipToNext() {
        playerService.skipToNext()
    }

    override suspend fun skipToPrevious() {
        playerService.skipToPrevious()
    }

    private suspend fun collectPlayerServiceState() {
        playerService.playerState.collect(_playerState::emit)
    }

    private suspend fun collectPlayerServiceEvent() {
        playerService.playerEvent.collect(_playerEvent::emit)
    }
}