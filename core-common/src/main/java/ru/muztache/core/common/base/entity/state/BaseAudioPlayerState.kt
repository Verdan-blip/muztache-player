package ru.muztache.core.common.base.entity.state

import ru.muztache.core.common.base.entity.BaseAudioItemInfoModel
import ru.muztache.core.common.base.entity.BasePlayingState
import ru.muztache.core.util.time.Milliseconds

data class BaseAudioPlayerState(
    val state: BasePlayingState = BasePlayingState.BUFFERING,
    val currentBaseAudioItem: BaseAudioItemInfoModel? = null,
    val currentProgress: Milliseconds = Milliseconds(value = 0)
)