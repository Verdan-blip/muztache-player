package ru.muztache.audio_player.impl.presentation.impl.controller

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.player.AudioPlayerEvent
import ru.muztache.audio_player.api.domain.player.AudioPlayerState
import ru.muztache.audio_player.api.domain.player.controller.AudioPlayerController
import ru.muztache.audio_player.impl.presentation.service.MediaSessionPlayerService
import ru.muztache.audio_player.impl.presentation.service.binder.AudioPlayerServiceBinder
import ru.muztache.audio_player.impl.presentation.service.expections.AudioPlayerStartingException
import ru.muztache.core.common.base.connection.FlowBasedServiceConnection
import ru.muztache.core.common.coroutines.scopes.globalScope
import ru.muztache.core.common.tools.sdk.runOnApi33
import ru.muztache.core.util.time.Milliseconds

class MediaSessionPlayerController(
    private val context: Context
) : AudioPlayerController {

    private var binder: AudioPlayerServiceBinder? = null

    private val intent: Intent = Intent(context, MediaSessionPlayerService::class.java)

    private val connection = FlowBasedServiceConnection<AudioPlayerServiceBinder> { binder ->
        binder is AudioPlayerServiceBinder
    }

    private var isPlayerServiceStarted: Boolean = false

    private val _playerState = MutableStateFlow(AudioPlayerState())
    override val playerState: StateFlow<AudioPlayerState>
        get() = _playerState

    private val _playerEvent = MutableSharedFlow<AudioPlayerEvent>()
    override val playerEvent: SharedFlow<AudioPlayerEvent>
        get() = _playerEvent


    private var playerStateCollectorJob: Job? = null

    private var playerEventCollectorJob: Job? = null

    override suspend fun start() {
        if (!isPlayerServiceStarted) {
            with(context) {
                runOnApi33 {
                    if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED
                    ) {
                        throw AudioPlayerStartingException(
                            "Permission for posting notifications was not granted"
                        )
                    }
                }

                startForegroundService(intent)
                bindService(intent, connection, BIND_SERVICE_FLAGS)
            }

            connection.awaitBinder().also { serviceBinder ->
                binder = serviceBinder
                serviceBinder.start()
                globalScope.launch {
                    playerStateCollectorJob = launch {
                        collectPlayerState(serviceBinder)
                    }
                    playerEventCollectorJob = launch {
                        collectPlayerEvent(serviceBinder)
                    }
                }
                isPlayerServiceStarted = true
            }
        } else {
            throw AudioPlayerStartingException("Audio Player service has been already started")
        }
    }

    override suspend fun stop() {
        binder?.stop()

        playerStateCollectorJob?.cancel()
        playerEventCollectorJob?.cancel()

        playerStateCollectorJob = null
        playerEventCollectorJob = null

        isPlayerServiceStarted = false
    }

    override suspend fun resume() {
        binder?.resume()
    }

    override suspend fun pause() {
        binder?.pause()
    }

    override suspend fun seekTo(millis: Milliseconds) {
        binder?.seekTo(millis)
    }

    override suspend fun play(audioItem: AudioItem) {
        binder?.play(audioItem)
    }

    override suspend fun play(audioItems: List<AudioItem>) {
        binder?.play(audioItems)
    }

    override suspend fun skipToNext() {
        binder?.skipToNext()
    }

    override suspend fun playNext(audioItem: AudioItem) {
        binder?.skipToNext()
    }

    override suspend fun playNext(audioItems: List<AudioItem>) {
        binder?.playNext(audioItems)
    }

    override suspend fun skipToPrevious() {
        binder?.skipToPrevious()
    }

    override suspend fun clearQueue() {
        binder?.clearQueue()
    }

    private suspend fun collectPlayerState(binder: AudioPlayerServiceBinder) {
        binder.playerState.collect(_playerState::emit)
    }

    private suspend fun collectPlayerEvent(binder: AudioPlayerServiceBinder) {
        binder.playerEvent.collect(_playerEvent::emit)
    }

    companion object {

        const val BIND_SERVICE_FLAGS = 0
    }
}