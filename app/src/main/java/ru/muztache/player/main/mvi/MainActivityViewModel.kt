package ru.muztache.player.main.mvi

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.muztache.audio_player.api.domain.controller.AudioPlayerController
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

    override fun play() {
        viewModelScope.launch {
            audioPlayerController.play()
        }
    }

    override fun play(audioItem: BaseAudioItemModel) {
        viewModelScope.launch {
            audioPlayerController.play(audioItem.toAudioItem())
        }
    }

    override fun play(audioItems: List<BaseAudioItemModel>) {
        viewModelScope.launch {
            audioPlayerController.play(audioItems.map { baseAudioItemModel ->
                baseAudioItemModel.toAudioItem()
            })
        }
    }

    override fun skipToNext() {
        viewModelScope.launch {
            audioPlayerController.skipToNext()
        }
    }

    override fun playNext(audioItem: BaseAudioItemModel) {
        viewModelScope.launch {
            audioPlayerController.playNext(audioItem.toAudioItem())
        }
    }

    override fun playNext(audioItems: List<BaseAudioItemModel>) {
        viewModelScope.launch {
            audioPlayerController.playNext(audioItems.map { baseAudioItemModel ->
                baseAudioItemModel.toAudioItem()
            })
        }
    }

    override fun skipToPrevious() {
        viewModelScope.launch {
            audioPlayerController.skipToPrevious()
        }
    }

    override fun pause() {
        viewModelScope.launch {
            audioPlayerController.pause()
        }
    }

    override fun seekTo(millis: Milliseconds) {
        viewModelScope.launch {
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