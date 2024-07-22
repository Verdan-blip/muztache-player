package ru.muztache.core.theme.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.annotation.StringRes

abstract class AbstractNotificationService(
    private val context: Context
) : NotificationService {

    protected val notificationManager: NotificationManager =
        context.getSystemService(NotificationManager::class.java)

    abstract val notificationChannel: NotificationChannel
}