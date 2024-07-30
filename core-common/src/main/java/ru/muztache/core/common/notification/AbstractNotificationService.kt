package ru.muztache.core.common.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

abstract class AbstractNotificationService(
    private val context: Context
) : NotificationService {

    protected val notificationManager: NotificationManager =
        context.getSystemService(NotificationManager::class.java)

    abstract val notificationChannel: NotificationChannel
}