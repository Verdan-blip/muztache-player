package ru.muztache.audio_player.impl.presentation.service

import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class AudioPlayerService : MediaSessionService() {

    private var mediaSession: MediaSession? = null

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession? = mediaSession

    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this)
            .build()
        mediaSession = MediaSession.Builder(this, player)
            .build()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        mediaSession?.apply {
            if (serviceShouldBeStopped(player)) { stopSelf() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession?.release()
        mediaSession = null
    }

    private fun serviceShouldBeStopped(player: Player): Boolean =
        !player.playWhenReady ||
                player.mediaItemCount == 0 ||
                player.playbackState == Player.STATE_ENDED
}