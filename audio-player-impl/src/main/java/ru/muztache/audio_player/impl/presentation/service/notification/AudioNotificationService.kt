package ru.muztache.audio_player.impl.presentation.service.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.session.MediaSession
import ru.muztache.audio_player.impl.R

class AudioNotificationService(
    mediaSession: MediaSession,
    private val context: Context
) {

    private val notificationChannelId =
        context.getString(R.string.media_session_notification_channel_id)

    private val notificationChannelName =
        context.getString(R.string.media_session_notification_channel_name)

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

    fun createNotification(): Notification {
        return Notification.Builder(context, notificationChannelId)
            .setOnlyAlertOnce(true)
            .setVisibility(Notification.VISIBILITY_PRIVATE)
            .setSmallIcon(android.R.drawable.stat_sys_data_bluetooth)
            .setColorized(true)
            .setStyle(notificationStyle)
            .build()
    }

    fun notify(id: Int, notification: Notification) {
        notificationManager.notify(id, notification)
    }
}