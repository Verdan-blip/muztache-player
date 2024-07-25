package ru.muztache.core.common.base.viewmodel

import kotlinx.coroutines.flow.StateFlow
import ru.muztache.core.common.base.entity.BaseAudioItemModel
import ru.muztache.core.common.base.entity.state.BaseAudioPlayerState
import ru.muztache.core.util.time.Milliseconds

abstract class AudioViewModel : BaseViewModel() {

    abstract val playerState: StateFlow<BaseAudioPlayerState>


    abstract fun play()

    abstract fun play(audioItem: BaseAudioItemModel)

    abstract fun play(audioItems: List<BaseAudioItemModel>)

    abstract fun skipToNext()

    abstract fun playNext(audioItem: BaseAudioItemModel)

    abstract fun playNext(audioItems: List<BaseAudioItemModel>)

    abstract fun skipToPrevious()

    abstract fun pause()

    abstract fun seekTo(millis: Milliseconds)

    abstract fun clearQueue()
}