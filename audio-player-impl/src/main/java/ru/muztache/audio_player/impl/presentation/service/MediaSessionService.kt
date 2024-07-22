package ru.muztache.audio_player.impl.presentation.service

import android.app.PendingIntent
import android.content.Intent
import android.media.AudioManager
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import kotlinx.coroutines.launch
import ru.muztache.audio_player.api.domain.player.AudioPlayerEvent
import ru.muztache.audio_player.impl.presentation.service.notification.AudioNotificationCommand
import ru.muztache.audio_player.impl.presentation.service.notification.AudioNotificationService
import ru.muztache.audio_player.impl.presentation.service.session.MediaSessionCallbackImpl
import ru.muztache.audio_player.impl.presentation.service.tools.extensions.getAudioNotificationCommand
import ru.muztache.audio_player.impl.presentation.service.tools.utils.AudioFocusUtils
import ru.muztache.audio_player.impl.presentation.service.tools.utils.MediaSessionUtils
import ru.muztache.core.common.contracts.ActivityStarter

class MediaSessionService : AudioPlayerService() {

    private var mediaSession: MediaSession? = null

    private var notificationService: AudioNotificationService? = null

    private val audioManager: AudioManager by lazy {
        getSystemService(AudioManager::class.java)
    }

    private val audioFocusRequest = AudioFocusUtils.createMediaAudioFocusRequest(
        onAudioFocusGain = { },
        onAudioFocusLossTransientWithDuck = { },
        onMissingFocus = { }
    )

    init {
        serviceScope.launch {
            syncPlayerState()
            collectPlayerEvent()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            val command = getAudioNotificationCommand()
            if (command == null) {
                startForeground(
                    MEDIA_SESSION_NOTIFICATION_ID,
                    notificationService?.createNotification(AudioNotificationService.AudioPlaybackState.LOADING)
                )
            } else {
                handleNotificationCommand(command)
            }
        }
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

    private fun handleNotificationCommand(command: AudioNotificationCommand) {
        when (command) {
            AudioNotificationCommand.RESUME -> {
                startForeground(MEDIA_SESSION_NOTIFICATION_ID, notificationService?.createNotification(AudioNotificationService.AudioPlaybackState.RESUMED))
                serviceScope.launch { resume() }
            }
            AudioNotificationCommand.PAUSE -> {
                startForeground(MEDIA_SESSION_NOTIFICATION_ID, notificationService?.createNotification(AudioNotificationService.AudioPlaybackState.PAUSED))
                serviceScope.launch { pause() }
            }
            AudioNotificationCommand.STOP -> { stopSelf() }
            AudioNotificationCommand.SKIP_TO_NEXT -> { serviceScope.launch { skipToNext() } }
            AudioNotificationCommand.SKIP_TO_PREVIOUS -> { serviceScope.launch { skipToPrevious() } }
        }
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
                    audioManager.abandonAudioFocusRequest(audioFocusRequest)
                }
                is AudioPlayerEvent.Resumed -> {
                    audioManager.requestAudioFocus(audioFocusRequest)
                }
                is AudioPlayerEvent.AudioItemChanged -> {
                    mediaSession?.apply {
                        val metadata = MediaSessionUtils.mediaMetadataBuilder
                            .putString(MediaMetadata.METADATA_KEY_TITLE, event.audioItemInfo.title)
                            .putString(MediaMetadata.METADATA_KEY_ARTIST, event.audioItemInfo.artists.joinToString(separator = " & "))
                            .putLong(MediaMetadata.METADATA_KEY_DURATION, event.audioItemInfo.duration.value)
                            .build()
                        val playbackState = PlaybackState.Builder()
                            .setState(PlaybackState.STATE_PLAYING, PlaybackState.PLAYBACK_POSITION_UNKNOWN, 1f)
                            .build()
                        isActive = true
                        setMetadata(metadata)
                        setPlaybackState(playbackState)
                    }
                }
                is AudioPlayerEvent.PlayingProgressChanged -> {
                    mediaSession?.setPlaybackState(PlaybackState.Builder()
                        .setState(PlaybackState.STATE_PLAYING, event.newProgress.value, 1f)
                        .build())
                }
                else -> Unit
            }
        }
    }

    private fun setupMediaSession(mediaSession: MediaSession) {
        val callback = audioPlayer?.let { player ->
            MediaSessionCallbackImpl(player, mediaSession)
        }
        val activityStarter = applicationContext as ActivityStarter
        mediaSession.setCallback(callback)
        mediaSession.setSessionActivity(
            PendingIntent.getActivity(
                applicationContext, 0,
                activityStarter.getIntentForMainActivity(), PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    companion object {

        const val MEDIA_SESSION_NOTIFICATION_ID = 69
        const val MEDIA_SESSION_TAG = "MediaSessionService"
    }
}