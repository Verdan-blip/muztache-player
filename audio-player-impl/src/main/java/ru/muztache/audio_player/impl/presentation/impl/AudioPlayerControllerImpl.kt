package ru.muztache.audio_player.impl.presentation.impl

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.player.AudioPlayerEvent
import ru.muztache.audio_player.api.domain.player.AudioPlayerState
import ru.muztache.audio_player.api.domain.player.controller.AudioPlayerController
import ru.muztache.audio_player.impl.presentation.service.MediaSessionService
import ru.muztache.audio_player.impl.presentation.service.binder.AudioPlayerServiceBinder
import ru.muztache.audio_player.impl.presentation.service.expections.AudioControllerNotConnectedException
import ru.muztache.audio_player.impl.presentation.service.expections.AudioPlayerStartingException
import ru.muztache.core.common.base.connection.FlowBasedServiceConnection
import ru.muztache.core.common.tools.sdk.runOnApi33
import ru.muztache.core.util.extensions.foldNullability
import ru.muztache.core.util.time.Milliseconds

class AudioPlayerControllerImpl(
    private val context: Context
) : AudioPlayerController {

    private val intent: Intent = Intent(context, MediaSessionService::class.java)

    private var binder: AudioPlayerServiceBinder? = null

    private val binderScope = CoroutineScope(context = Dispatchers.IO + SupervisorJob())

    private val connection = FlowBasedServiceConnection<AudioPlayerServiceBinder> { binder ->
        binder is AudioPlayerServiceBinder
    }

    private val _playerState = MutableStateFlow(AudioPlayerState())
    override val playerState: StateFlow<AudioPlayerState>
        get() = _playerState

    private val _playerEvent = MutableSharedFlow<AudioPlayerEvent>()
    override val playerEvent: SharedFlow<AudioPlayerEvent>
        get() = _playerEvent

    override suspend fun resume() {
        requiredPlayerConnection { binder -> binder.resume() }
    }

    override suspend fun pause() {
        requiredPlayerConnection { binder -> binder.pause() }
    }

    override suspend fun seekTo(millis: Milliseconds) {
        requiredPlayerConnection { binder -> binder.seekTo(millis) }
    }

    override suspend fun play(audioItem: AudioItem) {
        requiredPlayerConnection { binder -> binder.play(audioItem) }
    }

    override suspend fun play(audioItems: List<AudioItem>) {
        requiredPlayerConnection { binder -> binder.play(audioItems) }
    }

    override suspend fun skipToNext() {
        requiredPlayerConnection { binder -> binder.skipToNext() }
    }

    override suspend fun skipToPrevious() {
        requiredPlayerConnection { binder -> binder.skipToPrevious() }
    }

    override suspend fun playNext(audioItem: AudioItem) {
        requiredPlayerConnection { binder -> binder.playNext(audioItem) }
    }

    override suspend fun playNext(audioItems: List<AudioItem>) {
        requiredPlayerConnection { binder -> binder.playNext(audioItems) }
    }

    override suspend fun clearQueue() {
        requiredPlayerConnection { binder -> binder.clearQueue() }
    }

    override suspend fun start() {
        with(context) {
            runOnApi33 {
                if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_DENIED) {
                    throw AudioPlayerStartingException(
                        "Permission for posting notifications was not granted"
                    )
                }
            }

            startForegroundService(intent)
            bindService(intent, connection, BIND_SERVICE_FLAGS)

            connection.awaitBinder().also { serviceBinder ->
                binder = serviceBinder
                binderScope.launch {
                    launch { collectConnectionErrorEvents() }
                    launch { collectPlayerStates(serviceBinder) }
                    launch { collectPlayerEvents(serviceBinder) }
                }
                serviceBinder.start()
            }
        }
    }

    override suspend fun stop() {
        with(context) {
            unbindService(connection)
            stopService(intent)
        }
    }

    private suspend fun collectPlayerStates(binder: AudioPlayerServiceBinder) {
        binder.playerState.collect { state ->
            _playerState.emit(state)
        }
    }

    private suspend fun collectPlayerEvents(binder: AudioPlayerServiceBinder) {
        binder.playerEvent.collect { event ->
            _playerEvent.emit(event)
        }
    }

    private suspend fun collectConnectionErrorEvents() {
        connection.errorCallback.collect {
            binderScope.cancel()
        }
    }

    private suspend fun requiredPlayerConnection(block: suspend (AudioPlayerServiceBinder) -> Unit) {
        binder.foldNullability(
            onNull = { throw AudioControllerNotConnectedException() },
            onNotNull = { block(it) }
        )
    }

    companion object {

        const val BIND_SERVICE_FLAGS = 0
    }
}