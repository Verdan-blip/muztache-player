package ru.muztache.player.main.mapper

import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.entity.AudioItemInfo
import ru.muztache.audio_player.api.domain.controller.AudioPlayerState
import ru.muztache.audio_player.api.domain.controller.PlayingState
import ru.muztache.core.common.base.entity.BaseAudioItemInfoModel
import ru.muztache.core.common.base.entity.BaseAudioItemModel
import ru.muztache.core.common.base.entity.BasePlayingState
import ru.muztache.core.common.base.entity.state.BaseAudioPlayerState

fun BaseAudioItemModel.toAudioItem(): AudioItem = 
    AudioItem(
        id = id, 
        title = title, 
        artists = authors,
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
        state = state.toBasePlayingState(),
        currentBaseAudioItem = currentPlayingAudioItem?.toBaseAudioItemModelInfo(),
        currentProgress = currentProgress
    )

fun PlayingState.toBasePlayingState(): BasePlayingState {
    return when (this) {
        PlayingState.Playing -> BasePlayingState.RESUMED
        PlayingState.BUFFERING -> BasePlayingState.BUFFERING
        PlayingState.PAUSED -> BasePlayingState.PAUSED
    }
}