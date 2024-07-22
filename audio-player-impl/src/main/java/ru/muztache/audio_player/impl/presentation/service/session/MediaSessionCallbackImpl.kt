package ru.muztache.audio_player.impl.presentation.service.session

import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.util.Log
import kotlinx.coroutines.launch
import ru.muztache.audio_player.api.domain.player.AudioPlayer
import ru.muztache.audio_player.impl.presentation.service.tools.utils.MediaSessionUtils
import ru.muztache.core.common.coroutines.scopes.globalScope
import ru.muztache.core.common.delegates.state

class MediaSessionCallbackImpl(
    private val audioPlayer: AudioPlayer,
    private val mediaSession: MediaSession
) : MediaSession.Callback() {

    private val playerState by state(audioPlayer.playerState)

    override fun onPlay() {
        Log.d("MEDIA_SESSION_CALLBACK", "onPlay")
        playerState.currentPlayingAudioItem?.apply {
            val metadata = MediaSessionUtils.mediaMetadataBuilder
                .putString(MediaMetadata.METADATA_KEY_TITLE, title)
                .putString(MediaMetadata.METADATA_KEY_ARTIST, artists.joinToString(separator = " & "))
                .putLong(MediaMetadata.METADATA_KEY_DURATION, duration.value)
                .putString(MediaMetadata.METADATA_KEY_ART_URI, coverUri)
                .build()
            mediaSession.isActive = true
            mediaSession.setMetadata(metadata)
        }
        globalScope.launch { audioPlayer.resume() }
    }

    override fun onPause() {
        Log.d("MEDIA_SESSION_CALLBACK", "onPause")
        playerState.currentPlayingAudioItem?.apply {
            globalScope.launch { audioPlayer.pause() }
            mediaSession.setPlaybackState(
                MediaSessionUtils.playbackStateBuilder
                    .setState(
                        PlaybackState.STATE_PAUSED,
                        audioPlayer.playerState.value.currentProgress.value,
                        PLAYBACK_SPEED
                    )
                    .build()
            )
        }
    }

    override fun onSkipToNext() {
        Log.d("MEDIA_SESSION_CALLBACK", "onSkipToNext")
        globalScope.launch { audioPlayer.skipToNext() }
        mediaSession.setPlaybackState(
            MediaSessionUtils.playbackStateBuilder
                .setState(
                    PlaybackState.STATE_SKIPPING_TO_NEXT,
                    PlaybackState.PLAYBACK_POSITION_UNKNOWN,
                    PLAYBACK_SPEED
                )
                .build()
        )
    }

    override fun onSkipToPrevious() {
        Log.d("MEDIA_SESSION_CALLBACK", "onSkipToPrevious")
        globalScope.launch { audioPlayer.skipToPrevious() }
        mediaSession.setPlaybackState(
            MediaSessionUtils.playbackStateBuilder
                .setState(
                    PlaybackState.STATE_SKIPPING_TO_PREVIOUS,
                    PlaybackState.PLAYBACK_POSITION_UNKNOWN,
                    PLAYBACK_SPEED
                )
                .build()
        )
    }

    override fun onRewind() {
        Log.d("MEDIA_SESSION_CALLBACK", "onRewind")
        globalScope.launch { audioPlayer.clearQueue() }
        mediaSession.setPlaybackState(
            MediaSessionUtils.playbackStateBuilder
                .setState(
                    PlaybackState.STATE_REWINDING,
                    PlaybackState.PLAYBACK_POSITION_UNKNOWN,
                    PLAYBACK_SPEED
                )
                .build()
        )
    }

    override fun onStop() {
        Log.d("MEDIA_SESSION_CALLBACK", "onStop")
        globalScope.launch { audioPlayer.stop() }
        mediaSession.isActive = false
        mediaSession.setPlaybackState(
            MediaSessionUtils.playbackStateBuilder
                .setState(
                    PlaybackState.STATE_STOPPED,
                    PlaybackState.PLAYBACK_POSITION_UNKNOWN,
                    PLAYBACK_SPEED
                )
                .build()
        )
    }

    companion object {

        const val PLAYBACK_SPEED = 1f
    }
}