package ru.muztache.audio_player.impl.presentation.impl

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.controller.AudioPlayerEvent
import ru.muztache.audio_player.api.domain.controller.AudioPlayerState
import ru.muztache.audio_player.api.domain.controller.PlayingState
import ru.muztache.audio_player.api.domain.controller.AudioPlayerController
import ru.muztache.audio_player.impl.presentation.mapper.toAudioItemInfo
import ru.muztache.audio_player.impl.presentation.mapper.toMediaItem
import ru.muztache.audio_player.impl.presentation.mapper.toMediaItemList
import ru.muztache.audio_player.impl.presentation.service.AudioPlayerService
import ru.muztache.core.common.contracts.ResourceManager
import ru.muztache.core.util.time.Milliseconds
import ru.muztache.core.util.time.toMilliseconds
import ru.muztache.core.util.time.toProgress
import kotlin.time.Duration.Companion.seconds

class AudioPlayerControllerImpl(
    private val context: Context,
    private val resourceManager: ResourceManager
) : AudioPlayerController {

    private val sessionToken = SessionToken(
        context, ComponentName(context, AudioPlayerService::class.java)
    )

    private var mediaController: MediaController? = null

    private var mediaControllerFuture: ListenableFuture<MediaController>? = null

    private val controllerScope = CoroutineScope(context = Dispatchers.Main + SupervisorJob())


    private val _playerState = MutableStateFlow(AudioPlayerState())
    override val playerState: StateFlow<AudioPlayerState>
        get() = _playerState

    private val _playerEvent = MutableSharedFlow<AudioPlayerEvent>()
    override val playerEvent: SharedFlow<AudioPlayerEvent>
        get() = _playerEvent

    init {
        mediaControllerFuture = MediaController.Builder(context, sessionToken)
            .buildAsync()
        mediaControllerFuture?.addListener({
            mediaController = mediaControllerFuture?.get()?.apply {
                playWhenReady = true
                prepare()
                controllerScope.launch {
                    setupPlayerListeners()
                    launch { collectPlayingPosition() }
                    launch { collectBufferedPosition() }

                    _playerEvent.emit(AudioPlayerEvent.Initialized)
                    _playerState.emit(AudioPlayerState(isActive = true))
                }
            }
        }, MoreExecutors.directExecutor())
    }

    override fun release() {
        mediaControllerFuture?.also(MediaController::releaseFuture)
        controllerScope.launch {
            _playerEvent.emit(AudioPlayerEvent.Stopped)
            _playerState.emit(AudioPlayerState(isActive = false))
        }
        controllerScope.cancel()
    }

    override fun play() {
        mediaController?.play()
    }

    override fun pause() {
        mediaController?.pause()
    }

    override fun seekTo(millis: Milliseconds) {
        mediaController?.seekTo(millis.value)
    }

    override fun play(audioItem: AudioItem) {
        mediaController?.setMediaItem(audioItem.toMediaItem(resourceManager))
    }

    override fun play(audioItems: List<AudioItem>) {
        mediaController?.setMediaItems(audioItems.toMediaItemList(resourceManager))
    }

    override fun skipToNext() {
        mediaController?.seekToNext()
    }

    override fun playNext(audioItem: AudioItem) {
        mediaController?.addMediaItem(audioItem.toMediaItem(resourceManager))
    }

    override fun playNext(audioItems: List<AudioItem>) {
        mediaController?.addMediaItems(audioItems.toMediaItemList(resourceManager))
    }

    override fun skipToPrevious() {
        mediaController?.seekToPrevious()
    }

    override fun clearQueue() {
        mediaController?.clearMediaItems()
    }

    private fun setupPlayerListeners() {
        mediaController?.addListener(object : Player.Listener {

            override fun onPlayerError(error: PlaybackException) {
                controllerScope.launch {
                    _playerEvent.emit(AudioPlayerEvent.PlaybackError(error))
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                val state = if (isPlaying) {
                    PlayingState.Playing
                } else {
                    if (mediaController?.playbackState == Player.STATE_BUFFERING) {
                        PlayingState.BUFFERING
                    } else {
                        PlayingState.PAUSED
                    }
                }
                controllerScope.launch {
                    _playerEvent.emit(AudioPlayerEvent.PlayingStateChanged(state))
                    _playerState.emit(AudioPlayerState(state = state))
                }
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                controllerScope.launch {
                    mediaItem?.apply {
                        mediaController?.also { controller ->
                            val audioItemInfo = toAudioItemInfo(resourceManager, controller)
                            _playerEvent.emit(
                                AudioPlayerEvent.AudioItemChanged(audioItemInfo)
                            )
                            _playerState.emit(
                                AudioPlayerState(currentPlayingAudioItem = audioItemInfo)
                            )
                        }
                    }
                }
            }
        })
    }

    private suspend fun collectPlayingPosition() {
        while (true) {
            mediaController?.apply {
                if (bufferedPosition != duration) {
                    _playerEvent.emit(AudioPlayerEvent.BufferingPositionChanged(bufferedPercentage.toProgress()))
                }
                delay(BUFFERED_POSITION_COLLECTING_DELAY)
            }
        }
    }

    private suspend fun collectBufferedPosition() {
        while (true) {
            mediaController?.apply {
                if (isPlaying) {
                    _playerEvent.emit(AudioPlayerEvent.PlayingPositionChanged(currentPosition.toMilliseconds()))
                }
                delay(PLAYING_POSITION_COLLECTING_DELAY)
            }
        }
    }

    companion object {

        val PLAYING_POSITION_COLLECTING_DELAY = 1.seconds
        val BUFFERED_POSITION_COLLECTING_DELAY = 1.seconds
    }
}