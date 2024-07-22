package ru.muztache.audio_player.impl.presentation.service.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.muztache.audio_player.impl.presentation.service.MediaSessionService
import ru.muztache.audio_player.impl.presentation.service.tools.extensions.getAudioNotificationCommand
import ru.muztache.audio_player.impl.presentation.service.tools.extensions.putAudioNotificationCommand

class AudioButtonReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.getAudioNotificationCommand()?.also { command ->
            val serviceIntent = Intent(context, MediaSessionService::class.java)
                .putAudioNotificationCommand(command)
            context?.startForegroundService(serviceIntent)
        }
    }

    companion object {

        fun createPendingIntent(context: Context, intent: Intent): PendingIntent {
            return PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}