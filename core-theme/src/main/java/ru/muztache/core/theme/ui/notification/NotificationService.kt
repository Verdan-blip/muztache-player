package ru.muztache.core.theme.ui.notification

import android.app.Notification

interface NotificationService {

    fun createNotification(): Notification
}