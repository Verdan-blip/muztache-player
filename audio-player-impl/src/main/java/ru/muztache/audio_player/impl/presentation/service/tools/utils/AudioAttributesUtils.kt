package ru.muztache.audio_player.impl.presentation.service.tools.utils

import android.media.AudioAttributes

object AudioAttributesUtils {

    val mediaAudioAttributes: AudioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .build()
}