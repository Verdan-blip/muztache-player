package ru.muztache.audio_player.impl.presentation.service

import android.app.PendingIntent
import android.content.Intent
import android.media.AudioManager
import android.media.session.MediaSession
import android.os.IBinder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.muztache.audio_player.api.domain.player.AudioPlayerEvent
import ru.muztache.audio_player.impl.presentation.service.notification.AudioNotificationService
import ru.muztache.audio_player.impl.presentation.service.session.MediaSessionCallbackImpl
import ru.muztache.audio_player.impl.presentation.service.tools.extensions.setPlaybackStatePaused
import ru.muztache.audio_player.impl.presentation.service.tools.extensions.setPlaybackStatePending
import ru.muztache.audio_player.impl.presentation.service.tools.extensions.setMetadataFromAudioItem
import ru.muztache.audio_player.impl.presentation.service.tools.extensions.setPlaybackStatePlaying
import ru.muztache.audio_player.impl.presentation.service.tools.utils.AudioFocusUtils
import ru.muztache.core.common.contracts.ActivityStarter

class MediaSessionPlayerService : AudioPlayerService() {

    var mediaSession: MediaSession? = null

    private var notificationService: AudioNotificationService? = null

    private val audioManager: AudioManager by lazy {
        getSystemService(AudioManager::class.java)
    }

    private val audioFocusRequest = AudioFocusUtils.createMediaAudioFocusRequest(
        onAudioFocusGain = {
            serviceScope.launch { resume() }
        },
        onAudioFocusLossTransientWithDuck = {
        },
        onMissingFocus = { serviceScope.launch { pause() }  }
    )

    init {
        serviceScope.launch {
            syncPlayerState()
            collectPlayerEvent()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(MEDIA_SESSION_NOTIFICATION_ID, notificationService?.createNotification())
        return START_REDELIVER_INTENT
    }

    override fun onCreate() {
        super.onCreate()
        MediaSession(applicationContext, MEDIA_SESSION_TAG).also { session ->
            setupMediaSession(session)
            this.mediaSession = session
            this.notificationService = AudioNotificationService(context = this, mediaSession = session)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession?.release()
        mediaSession = null
    }

    private fun syncPlayerState() {
        if (playerState.value.isPaused) {
            audioManager.abandonAudioFocusRequest(audioFocusRequest)
        } else {
            audioManager.requestAudioFocus(audioFocusRequest)
        }
    }

    private suspend fun collectPlayerEvent() {
        playerEvent.collect { event ->
            when (event) {
                is AudioPlayerEvent.Paused -> {
                    mediaSession?.setPlaybackStatePaused(playerState.value)
                    audioManager.abandonAudioFocusRequest(audioFocusRequest)
                }
                is AudioPlayerEvent.Resumed -> {
                    mediaSession?.setPlaybackStatePlaying(playerState.value)
                    audioManager.requestAudioFocus(audioFocusRequest)
                }
                is AudioPlayerEvent.AudioItemChanged -> {
                    mediaSession?.apply {
                        setPlaybackStatePending(playerState.value)
                        serviceScope.launch(Dispatchers.IO) {
                            setMetadataFromAudioItem(event.audioItemInfo)
                            setPlaybackStatePlaying(playerState.value)
                            updateNotification()
                        }
                    }
                }
                is AudioPlayerEvent.BeginningOfQueueReached -> {
                    mediaSession?.apply {

                    }
                }
                else -> Unit
            }
        }
    }

    private fun setupMediaSession(mediaSession: MediaSession) {
        mediaSession.setCallback(MediaSessionCallbackImpl(this))
        mediaSession.setSessionActivity(
            PendingIntent.getActivity(
                applicationContext,
                0,
                (applicationContext as ActivityStarter).getIntentForMainActivity(),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
        mediaSession.setPlaybackStatePending(playerState.value)
    }

    private fun updateNotification() {
        notificationService?.apply {
            notify(MEDIA_SESSION_NOTIFICATION_ID, createNotification())
        }
    }

    companion object {

        const val MEDIA_SESSION_NOTIFICATION_ID = 69
        const val MEDIA_SESSION_TAG = "MediaSessionService"
    }
}