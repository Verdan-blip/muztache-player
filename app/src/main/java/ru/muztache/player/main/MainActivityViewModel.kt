package ru.muztache.player.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.muztache.audio_player.api.domain.player.controller.AudioPlayerController
import ru.muztache.core.common.base.entity.BaseAudioItemModel
import ru.muztache.core.common.base.entity.state.BaseAudioPlayerState
import ru.muztache.core.common.base.viewmodel.AudioViewModel
import ru.muztache.core.util.time.Milliseconds
import ru.muztache.player.main.mapper.toAudioItem
import ru.muztache.player.main.mapper.toBaseAudioPlayerState

class MainActivityViewModel(
    private val audioPlayerController: AudioPlayerController
) : AudioViewModel() {

    private val _playerState = MutableStateFlow(BaseAudioPlayerState())
    override val playerState: StateFlow<BaseAudioPlayerState>
        get() = _playerState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            collectPlayerState()
        }
    }

    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.start()
        }
    }

    override fun resume() {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.resume()
        }
    }

    override fun play(audioItem: BaseAudioItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.play(audioItem.toAudioItem())
        }
    }

    override fun play(audioItems: List<BaseAudioItemModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.play(audioItems.map { baseAudioItemModel ->
                baseAudioItemModel.toAudioItem()
            })
        }
    }

    override fun skipToNext() {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.skipToNext()
        }
    }

    override fun playNext(audioItem: BaseAudioItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.playNext(audioItem.toAudioItem())
        }
    }

    override fun playNext(audioItems: List<BaseAudioItemModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.playNext(audioItems.map { baseAudioItemModel ->
                baseAudioItemModel.toAudioItem()
            })
        }
    }

    override fun skipToPrevious() {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.skipToPrevious()
        }
    }

    override fun pause() {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.pause()
        }
    }

    override fun seekTo(millis: Milliseconds) {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.seekTo(millis)
        }
    }

    override fun clearQueue() {
        viewModelScope.launch(Dispatchers.IO) {
            audioPlayerController.clearQueue()
        }
    }

    private suspend fun collectPlayerState() {
        audioPlayerController.playerState.collect { state ->
            _playerState.emit(state.toBaseAudioPlayerState())
        }
    }
}