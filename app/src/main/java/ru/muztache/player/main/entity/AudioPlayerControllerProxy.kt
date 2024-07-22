package ru.muztache.player.main.entity

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.player.AudioPlayerEvent
import ru.muztache.audio_player.api.domain.player.AudioPlayerState
import ru.muztache.audio_player.api.domain.player.controller.AudioPlayerController
import ru.muztache.core.util.time.Milliseconds

class AudioPlayerControllerProxy(
    private val audioPlayerController: AudioPlayerController
) : AudioPlayerController {

    override val playerState: StateFlow<AudioPlayerState> =
        audioPlayerController.playerState

    override val playerEvent: SharedFlow<AudioPlayerEvent> =
        audioPlayerController.playerEvent

    private var isPlayerStarted: Boolean = false

    override suspend fun resume() {
        lazilyStarted {
            audioPlayerController.resume()
        }
    }

    override suspend fun pause() {
        lazilyStarted {
            audioPlayerController.pause()
        }
    }

    override suspend fun seekTo(millis: Milliseconds) {
        lazilyStarted {
            audioPlayerController.seekTo(millis)
        }
    }

    override suspend fun play(audioItem: AudioItem) {
        lazilyStarted {
            audioPlayerController.play(audioItem)
        }
    }

    override suspend fun play(audioItems: List<AudioItem>) {
        lazilyStarted {
            audioPlayerController.play(audioItems)
        }
    }

    override suspend fun skipToNext() {
        lazilyStarted {
            audioPlayerController.skipToNext()
        }
    }

    override suspend fun playNext(audioItem: AudioItem) {
        lazilyStarted {
            audioPlayerController.playNext(audioItem)
        }
    }

    override suspend fun playNext(audioItems: List<AudioItem>) {
        lazilyStarted {
            audioPlayerController.playNext(audioItems)
        }
    }

    override suspend fun skipToPrevious() {
        lazilyStarted {
            audioPlayerController.skipToPrevious()
        }
    }

    override suspend fun clearQueue() {
        lazilyStarted {
            audioPlayerController.clearQueue()
        }
    }

    override suspend fun start() {
        audioPlayerController.start()
        isPlayerStarted = true
    }

    override suspend fun stop() {
        audioPlayerController.stop()
        isPlayerStarted = false
    }

    private fun lazilyStarted(block: suspend () -> Unit) {
        runBlocking {
            if (!isPlayerStarted) {
                start()
            }
            block()
        }
    }
}