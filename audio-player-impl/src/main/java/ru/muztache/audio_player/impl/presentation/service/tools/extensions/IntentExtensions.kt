package ru.muztache.audio_player.impl.presentation.service.tools.extensions

import android.content.Intent
import android.os.Build
import ru.muztache.audio_player.impl.presentation.service.notification.AudioNotificationCommand
import ru.muztache.audio_player.impl.presentation.service.notification.AudioNotificationService

const val KEY_AUDIO_NOTIFICATION_COMMAND = "audioNotificationCommand"

fun Intent.getAudioNotificationCommand(): AudioNotificationCommand? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(
            KEY_AUDIO_NOTIFICATION_COMMAND,
            AudioNotificationCommand::class.java
        )
    } else {
        getSerializableExtra(
            KEY_AUDIO_NOTIFICATION_COMMAND,
        ) as? AudioNotificationCommand
    }

fun Intent.putAudioNotificationCommand(command: AudioNotificationCommand): Intent =
    putExtra(KEY_AUDIO_NOTIFICATION_COMMAND, command)