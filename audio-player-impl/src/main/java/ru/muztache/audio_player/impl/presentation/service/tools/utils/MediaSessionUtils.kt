package ru.muztache.audio_player.impl.presentation.service.tools.utils

import android.media.MediaMetadata
import android.media.session.PlaybackState

object MediaSessionUtils {

    val mediaMetadataBuilder = MediaMetadata.Builder()

    val playbackStateBuilder = PlaybackState.Builder()
        .setActions(PlaybackState.ACTION_PLAY
                    or PlaybackState.ACTION_STOP
                    or PlaybackState.ACTION_PAUSE
                    or PlaybackState.ACTION_PLAY_PAUSE
                    or PlaybackState.ACTION_SKIP_TO_NEXT
                    or PlaybackState.ACTION_SKIP_TO_PREVIOUS
        )
}