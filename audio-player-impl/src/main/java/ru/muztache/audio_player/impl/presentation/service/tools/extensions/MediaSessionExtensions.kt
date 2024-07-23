package ru.muztache.audio_player.impl.presentation.service.tools.extensions

import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import ru.muztache.audio_player.api.domain.entity.AudioItemInfo
import ru.muztache.audio_player.api.domain.player.AudioPlayerState
import ru.muztache.audio_player.impl.presentation.service.tools.utils.MediaSessionUtils
import ru.muztache.audio_player.impl.presentation.service.tools.utils.PlaybackStateUtils
import ru.muztache.core.common.tools.bitmap.BitmapUtils

suspend fun MediaSession.setMetadataFromAudioItem(audioItem: AudioItemInfo) {
    with(audioItem) {
        val metadata = MediaSessionUtils.mediaMetadataBuilder
            .putString(MediaMetadata.METADATA_KEY_TITLE, title)
            .putLong(MediaMetadata.METADATA_KEY_DURATION, duration.value)
            .putString(MediaMetadata.METADATA_KEY_ARTIST, artists.joinToString(separator = " & "))
            .putBitmap(MediaMetadata.METADATA_KEY_ART, BitmapUtils.loadFromUri(coverUri))
            .build()
        setMetadata(metadata)
    }
}

fun MediaSession.setPlaybackStatePlaying(audioPlayerState: AudioPlayerState) {
    with(audioPlayerState) {
        val playbackState = PlaybackStateUtils.playbackStateBuilder
            .setState(
                if (isPaused) PlaybackState.STATE_PAUSED else PlaybackState.STATE_PLAYING,
                audioPlayerState.currentProgress.value,
                audioPlayerState.playingSpeed
            )
            .build()
        setPlaybackState(playbackState)
    }
}

fun MediaSession.setPlaybackStatePlayingMissingNext(audioPlayerState: AudioPlayerState) {
    setPlaybackState(PlaybackStateUtils.playbackBuilderMissingNext
        .setState(
            PlaybackState.STATE_PLAYING,
            PlaybackState.PLAYBACK_POSITION_UNKNOWN,
            audioPlayerState.playingSpeed
        )
        .build())
}

fun MediaSession.setPlaybackStatePending(audioPlayerState: AudioPlayerState) {
    setPlaybackState(PlaybackStateUtils.playbackStateBuilder
        .setState(
            PlaybackState.STATE_PAUSED,
            PlaybackState.PLAYBACK_POSITION_UNKNOWN,
            audioPlayerState.playingSpeed
        )
        .build()
    )
}

fun MediaSession.setPlaybackStatePaused(audioPlayerState: AudioPlayerState) {
    setPlaybackState(PlaybackStateUtils.playbackStateBuilder
        .setState(
            PlaybackState.STATE_PAUSED,
            audioPlayerState.currentProgress.value,
            audioPlayerState.playingSpeed
        )
        .build()
    )
}

fun MediaSession.setStateResumed(audioPlayerState: AudioPlayerState) {
    setPlaybackState(PlaybackStateUtils.playbackStateBuilder
        .setState(
            PlaybackState.STATE_PLAYING,
            audioPlayerState.currentProgress.value,
            audioPlayerState.playingSpeed
        )
        .build()
    )
}