package ru.muztache.audio_player.impl.presentation.service

import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.player.AudioPlayer
import ru.muztache.audio_player.api.domain.player.AudioPlayerEvent
import ru.muztache.audio_player.api.domain.player.AudioPlayerState
import ru.muztache.audio_player.impl.presentation.impl.player.AudioPlayerImpl
import ru.muztache.audio_player.impl.presentation.service.binder.AudioPlayerServiceBinder
import ru.muztache.core.common.base.service.BaseService
import ru.muztache.core.util.time.Milliseconds

abstract class AudioPlayerService : BaseService(), AudioPlayer {

    private var audioPlayer: AudioPlayer? = null

    private var binder: AudioPlayerServiceBinder? = null

    private val _playerState = MutableStateFlow(AudioPlayerState())
    override val playerState: StateFlow<AudioPlayerState>
        get() = _playerState

    private val _playerEvent = MutableSharedFlow<AudioPlayerEvent>()
    override val playerEvent: SharedFlow<AudioPlayerEvent>
        get() = _playerEvent

    override fun onCreate() {
        super.onCreate()
        audioPlayer = AudioPlayerImpl(applicationContext).also { player ->
            serviceScope.launch {
                launch { collectPlayerState(player) }
                launch { collectPlayerEvent(player) }
            }
        }
        binder = AudioPlayerServiceBinder(service = this)
    }

    override fun onBind(intent: Intent?): IBinder? = binder

    override fun onDestroy() {
        super.onDestroy()
        runBlocking { audioPlayer?.stop() }
        audioPlayer = null
        binder = null
    }

    override suspend fun start() {
        audioPlayer?.start()
    }

    override suspend fun stop() {
        audioPlayer?.stop()
    }

    override suspend fun resume() {
        audioPlayer?.resume()
    }

    override suspend fun pause() {
        audioPlayer?.pause()
    }

    override suspend fun seekTo(millis: Milliseconds) {
        audioPlayer?.seekTo(millis)
    }

    override suspend fun play(audioItem: AudioItem) {
        audioPlayer?.play(audioItem)
    }

    override suspend fun play(audioItems: List<AudioItem>) {
        audioPlayer?.play(audioItems)
    }

    override suspend fun skipToNext() {
        audioPlayer?.skipToNext()
    }

    override suspend fun playNext(audioItem: AudioItem) {
        audioPlayer?.playNext(audioItem)
    }

    override suspend fun playNext(audioItems: List<AudioItem>) {
        audioPlayer?.playNext(audioItems)
    }

    override suspend fun skipToPrevious() {
        audioPlayer?.skipToPrevious()
    }

    override suspend fun clearQueue() {
        audioPlayer?.clearQueue()
    }

    private suspend fun collectPlayerState(audioPlayer: AudioPlayer) {
        audioPlayer.playerState.collect { state ->
            _playerState.emit(state)
        }
    }

    private suspend fun collectPlayerEvent(audioPlayer: AudioPlayer) {
        audioPlayer.playerEvent.collect(_playerEvent::emit)
    }
}