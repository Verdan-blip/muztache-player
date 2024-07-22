package ru.muztache.audio_player.impl.presentation.service.tools.utils

import android.media.AudioFocusRequest
import android.media.AudioManager

object AudioFocusUtils {

    fun createMediaAudioFocusRequest(
        onAudioFocusGain: () -> Unit,
        onAudioFocusLossTransientWithDuck: () -> Unit,
        onMissingFocus: () -> Unit
    ): AudioFocusRequest {
        return AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(AudioAttributesUtils.mediaAudioAttributes)
            .setWillPauseWhenDucked(true)
            .setOnAudioFocusChangeListener { focusChange ->
                when (focusChange) {
                    AudioManager.AUDIOFOCUS_GAIN -> onAudioFocusGain()
                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> onAudioFocusLossTransientWithDuck()
                    else -> onMissingFocus()
                }
            }
            .build()
    }

    fun requestAudioFocus(
        audioManager: AudioManager,
        audioFocusRequest: AudioFocusRequest
    ): Boolean {
        return audioManager.requestAudioFocus(audioFocusRequest) ==
                AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }
}