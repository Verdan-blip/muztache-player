package ru.muztache.core.common.notification

import android.app.Notification

interface NotificationService {

    fun createNotification(): Notification
}