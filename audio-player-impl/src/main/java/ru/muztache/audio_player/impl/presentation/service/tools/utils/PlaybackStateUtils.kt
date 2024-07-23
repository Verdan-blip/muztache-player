package ru.muztache.audio_player.impl.presentation.service.tools.utils

import android.media.session.PlaybackState

object PlaybackStateUtils {

    val playbackStateBuilder = PlaybackState.Builder()
        .setActions(
            PlaybackState.ACTION_PLAY
                    or PlaybackState.ACTION_STOP
                    or PlaybackState.ACTION_PAUSE
                    or PlaybackState.ACTION_PLAY_PAUSE
                    or PlaybackState.ACTION_SKIP_TO_NEXT
                    or PlaybackState.ACTION_SKIP_TO_PREVIOUS
                    or PlaybackState.ACTION_SEEK_TO
        )

    val playbackBuilderMissingPrevious = PlaybackState.Builder()
        .setActions(
            PlaybackState.ACTION_PLAY
                or PlaybackState.ACTION_STOP
                or PlaybackState.ACTION_PAUSE
                or PlaybackState.ACTION_PLAY_PAUSE
                or PlaybackState.ACTION_SKIP_TO_NEXT
                or PlaybackState.ACTION_SEEK_TO)

    val playbackBuilderMissingNext = PlaybackState.Builder()
        .setActions(
            PlaybackState.ACTION_PLAY
                or PlaybackState.ACTION_STOP
                or PlaybackState.ACTION_PAUSE
                or PlaybackState.ACTION_PLAY_PAUSE
                or PlaybackState.ACTION_SKIP_TO_PREVIOUS
                or PlaybackState.ACTION_SEEK_TO)

    val playbackBuilderOneAudio = PlaybackState.Builder()
        .setActions(
            PlaybackState.ACTION_PLAY
                or PlaybackState.ACTION_STOP
                or PlaybackState.ACTION_PAUSE
                or PlaybackState.ACTION_PLAY_PAUSE
                or PlaybackState.ACTION_SEEK_TO)
}