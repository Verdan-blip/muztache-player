package ru.muztache.core.common.base.entity.state

import ru.muztache.core.common.base.entity.BaseAudioItemInfoModel
import ru.muztache.core.util.time.Milliseconds

data class BaseAudioPlayerState(
    val isPaused: Boolean = true,
    val currentBaseAudioItem: BaseAudioItemInfoModel? = null,
    val currentProgress: Milliseconds = Milliseconds(value = 0)
)