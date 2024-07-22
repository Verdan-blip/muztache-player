package ru.muztache.audio_player.api.domain.entity

import ru.muztache.core.util.time.Milliseconds

data class AudioItemInfo(
    val id: Long,
    val title: String,
    val artists: List<String>,
    val audioUri: String,
    val coverUri: String,
    val duration: Milliseconds
)