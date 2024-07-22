package ru.muztache.player.main.mapper

import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.entity.AudioItemInfo
import ru.muztache.audio_player.api.domain.player.AudioPlayerState
import ru.muztache.core.common.base.entity.BaseAudioItemInfoModel
import ru.muztache.core.common.base.entity.BaseAudioItemModel
import ru.muztache.core.common.base.entity.state.BaseAudioPlayerState

fun BaseAudioItemModel.toAudioItem(): AudioItem = 
    AudioItem(
        id = id, 
        title = title, 
        authors = authors, 
        audioUri = audioUri, 
        coverUri = coverUri
    )

fun AudioItemInfo.toBaseAudioItemModelInfo(): BaseAudioItemInfoModel =
    BaseAudioItemInfoModel(
        id = id,
        title = title,
        authors = artists,
        audioUri = audioUri,
        coverUri = coverUri,
        duration = duration
    )

fun BaseAudioItemInfoModel.toAudioItemInfo(): AudioItemInfo =
    AudioItemInfo(
        id = id,
        title = title,
        artists = authors,
        audioUri = audioUri,
        coverUri = coverUri,
        duration = duration
    )

fun AudioPlayerState.toBaseAudioPlayerState(): BaseAudioPlayerState =
    BaseAudioPlayerState(
        isPaused = isPaused,
        currentBaseAudioItem = currentPlayingAudioItem?.toBaseAudioItemModelInfo(),
        currentProgress = currentProgress
    )