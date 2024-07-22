package ru.muztache.audio_player.impl.presentation.impl.player

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
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
import ru.muztache.audio_player.api.domain.entity.AudioItemInfo
import ru.muztache.audio_player.api.domain.player.AudioPlayer
import ru.muztache.audio_player.api.domain.player.AudioPlayerEvent
import ru.muztache.audio_player.api.domain.player.AudioPlayerState
import ru.muztache.audio_player.impl.presentation.service.tools.utils.AudioAttributesUtils
import ru.muztache.audio_player.impl.presentation.service.tools.utils.toAudioItemInfo
import ru.muztache.core.util.time.Milliseconds
import ru.muztache.core.util.time.toMilliseconds
import ru.muztache.core.util.time.toProgress

class AudioPlayerImpl(
    private val context: Context
) : AudioPlayer {

    private var mediaPlayer: MediaPlayer? = null

    //Coroutine scopes
    private val mediaPlayerScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    //State
    private var isActive: Boolean = false

    private var isPaused: Boolean = false

    private var currentProgress: Milliseconds = Milliseconds(value = 0L)

    private var currentPlayingAudioItem: AudioItemInfo? = null

    private val remainingAudioItems: MutableList<AudioItem> = mutableListOf()

    private var currentPlayingIndex: Int = -1


    //Flow
    private val _playerState = MutableStateFlow(AudioPlayerState())
    override val playerState: StateFlow<AudioPlayerState>
        get() = _playerState

    private val _playerEvent = MutableSharedFlow<AudioPlayerEvent>()
    override val playerEvent: SharedFlow<AudioPlayerEvent>
        get() = _playerEvent

    override suspend fun start() {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(AudioAttributesUtils.mediaAudioAttributes)
            setupMediaPlayerListeners(this)
            mediaPlayerScope.launch { _playerEvent.emit(AudioPlayerEvent.Initialized) }
        }

        isActive = true
        _playerState.emit(getPlayerStateFromLatestChanges())
        _playerEvent.emit(AudioPlayerEvent.Started)
    }

    override suspend fun stop() {
        mediaPlayer?.release()
        mediaPlayer = null

        mediaPlayerScope.cancel()

        isActive = false
        _playerEvent.emit(AudioPlayerEvent.Stopped)
        _playerState.emit(getPlayerStateFromLatestChanges())
    }

    override suspend fun resume() {
        mediaPlayer?.start()

        isPaused = false
        _playerState.emit(getPlayerStateFromLatestChanges())
        _playerEvent.emit(AudioPlayerEvent.Resumed)
    }

    override suspend fun pause() {
        mediaPlayer?.pause()

        isPaused = true
        _playerState.emit(getPlayerStateFromLatestChanges())
        _playerEvent.emit(AudioPlayerEvent.Paused)
    }

    override suspend fun seekTo(millis: Milliseconds) {
        mediaPlayer?.seekTo(millis.value, MediaPlayer.SEEK_CLOSEST)

        currentProgress = millis
        _playerState.emit(getPlayerStateFromLatestChanges())
        _playerEvent.emit(AudioPlayerEvent.PlayingProgressChanged(millis))
    }

    override suspend fun play(audioItem: AudioItem) {
        mediaPlayer?.apply {
            playAudioItem(audioItem)
            currentPlayingIndex = 0
            remainingAudioItems.clear()
        }
    }

    override suspend fun play(audioItems: List<AudioItem>) {
        playAudioItem(audioItems.first())
        currentPlayingIndex = 0
        remainingAudioItems.clear()
        remainingAudioItems.addAll(audioItems)
    }

    override suspend fun skipToNext() {
        if (currentPlayingIndex < remainingAudioItems.size - 1) {
            playAudioItem(remainingAudioItems[++currentPlayingIndex])
        }
    }

    override suspend fun playNext(audioItem: AudioItem) {
        remainingAudioItems.add(0, audioItem)

        _playerState.emit(getPlayerStateFromLatestChanges())
        _playerEvent.emit(AudioPlayerEvent.RemainingAudioItemsChanged(remainingAudioItems))
    }

    override suspend fun playNext(audioItems: List<AudioItem>) {
        remainingAudioItems.addAll(currentPlayingIndex + 1, audioItems)

        _playerState.emit(getPlayerStateFromLatestChanges())
        _playerEvent.emit(AudioPlayerEvent.RemainingAudioItemsChanged(remainingAudioItems))
    }

    override suspend fun skipToPrevious() {
        if (currentPlayingIndex > 0) {
            playAudioItem(remainingAudioItems[--currentPlayingIndex])
        }
    }

    override suspend fun clearQueue() {
        remainingAudioItems.clear()

        _playerState.emit(getPlayerStateFromLatestChanges())
        _playerEvent.emit(AudioPlayerEvent.RemainingAudioItemsChanged(remainingAudioItems))
    }

    private suspend fun playAudioItem(audioItem: AudioItem) {
        mediaPlayer?.apply {
            reset()
            setDataSource(context, audioItem.audioUri.toUri())
            prepare()
            start()

            currentPlayingAudioItem = audioItem.toAudioItemInfo(
                duration.toMilliseconds()
            ).also { audioItemInfo ->
                _playerEvent.emit(AudioPlayerEvent.AudioItemChanged(audioItemInfo))
            }

            _playerState.emit(getPlayerStateFromLatestChanges())
        }
    }

    private fun setupMediaPlayerListeners(mediaPlayer: MediaPlayer) {
        mediaPlayer.apply {
            setOnBufferingUpdateListener { _, percent ->
                mediaPlayerScope.launch {
                    _playerEvent.emit(AudioPlayerEvent.BufferingProgressChanged(percent.toProgress()))
                }
            }
            setOnCompletionListener {
                mediaPlayerScope.launch { skipToNext() }
            }
            setOnErrorListener { _, _, _ ->
                mediaPlayerScope.launch {
                    _playerEvent.emit(AudioPlayerEvent.PlaybackError(Exception()))
                }
                true
            }
            setOnBufferingUpdateListener { _, percent ->
                mediaPlayerScope.launch {
                    _playerEvent.emit(AudioPlayerEvent.BufferingProgressChanged(percent.toProgress()))
                }
            }
            setOnSeekCompleteListener {
                mediaPlayerScope.launch {
                    _playerEvent.emit(AudioPlayerEvent.PlayingProgressChanged(currentPosition.toMilliseconds()))
                }
            }
            mediaPlayerScope.launch { collectPlayingPositionUpdates() }
        }
    }

    private suspend fun collectPlayingPositionUpdates() {
        while (true) {
            mediaPlayer?.apply {
                if (isPlaying) {
                    currentProgress = currentPosition.toMilliseconds()
                    _playerState.emit(getPlayerStateFromLatestChanges())
                    _playerEvent.emit(AudioPlayerEvent.PlayingProgressChanged(currentProgress))
                }
            }
            delay(timeMillis = PLAYING_PROGRESS_UPDATING_PERIOD.value)
        }
    }

    private fun getPlayerStateFromLatestChanges(): AudioPlayerState =
        AudioPlayerState(
            isActive = isActive,
            isPaused = isPaused,
            currentPlayingAudioItem = currentPlayingAudioItem,
            currentProgress = currentProgress,
            remainingAudioItems = remainingAudioItems
        )

    companion object {

        val PLAYING_PROGRESS_UPDATING_PERIOD = Milliseconds(value = 1_000)
    }
}