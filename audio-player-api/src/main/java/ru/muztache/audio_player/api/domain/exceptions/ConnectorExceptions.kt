package ru.muztache.audio_player.api.domain.exceptions

class AudioPlayerConnectionException(
    message: String
) : Exception(message)

class AudioPlayerConnectionTimeoutException(
    message: String
) : Exception(message)