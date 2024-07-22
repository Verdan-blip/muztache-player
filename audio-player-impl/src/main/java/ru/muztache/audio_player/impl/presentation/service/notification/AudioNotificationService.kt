package ru.muztache.audio_player.impl.presentation.service.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.session.MediaSession
import androidx.annotation.DrawableRes
import ru.muztache.audio_player.impl.R
import ru.muztache.audio_player.impl.presentation.service.receiver.AudioButtonReceiver
import ru.muztache.audio_player.impl.presentation.service.tools.extensions.putAudioNotificationCommand

class AudioNotificationService(
    private val context: Context,
    private val mediaSession: MediaSession) {

    private val notificationChannelId = context.getString(R.string.media_session_notification_channel_id)

    private val notificationChannelName = context.getString(R.string.media_session_notification_channel_name)

    private val notificationStyle = Notification.MediaStyle()
        .setMediaSession(mediaSession.sessionToken)

    private val notificationManager: NotificationManager = context
        .getSystemService(NotificationManager::class.java)

    init {
        val channel = NotificationChannel(
            notificationChannelId, notificationChannelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    fun createNotification(state: AudioPlaybackState): Notification {
        return Notification.Builder(context, notificationChannelId).apply {
            setOnlyAlertOnce(true)
            setVisibility(Notification.VISIBILITY_PRIVATE)
            setSmallIcon(android.R.drawable.stat_sys_data_bluetooth)
            addAction(createAction(
                iconId = android.R.drawable.ic_media_previous,
                title = "SkipToPrevious",
                intent = createPendingIntentForReceiver(AudioNotificationCommand.SKIP_TO_PREVIOUS)
            ))
            addAction(
                when (state) {
                    AudioPlaybackState.RESUMED -> {
                        createAction(
                            iconId = android.R.drawable.ic_media_pause,
                            title = "Playing",
                            intent = createPendingIntentForReceiver(AudioNotificationCommand.RESUME)
                        )
                    }
                    AudioPlaybackState.PAUSED -> {
                        createAction(
                            iconId = android.R.drawable.ic_popup_sync,
                            title = "Pause",
                            intent = createPendingIntentForReceiver(AudioNotificationCommand.PAUSE)
                        )
                    }
                    AudioPlaybackState.LOADING -> {
                        createAction(
                            iconId = android.R.drawable.ic_media_pause,
                            title = "Loading",
                            intent = null
                        )
                    }
                }
            )
            addAction(createAction(
                iconId = android.R.drawable.ic_media_next,
                title = "SkipToNext",
                intent = createPendingIntentForReceiver(AudioNotificationCommand.SKIP_TO_NEXT)
            ))
            setStyle(notificationStyle)
        }.build()
    }

    private fun createAction(
        @DrawableRes iconId: Int,
        title: String,
        intent: PendingIntent? = null
    ): Notification.Action {
        return Notification.Action.Builder(Icon.createWithResource(context, iconId), title, intent)
            .build()
    }

    private fun createPendingIntentForReceiver(command: AudioNotificationCommand): PendingIntent {
        val intent = Intent(context, AudioButtonReceiver::class.java)
            .putAudioNotificationCommand(command)
        return AudioButtonReceiver.createPendingIntent(context, intent)
    }

    enum class AudioPlaybackState {

        RESUMED, PAUSED, LOADING
    }
}