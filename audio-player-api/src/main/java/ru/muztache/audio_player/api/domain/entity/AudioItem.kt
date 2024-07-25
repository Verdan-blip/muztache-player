package ru.muztache.audio_player.api.domain.entity

data class AudioItem(
    val id: Long,
    val title: String,
    val artists: List<String>,
    val audioUri: String,
    val coverUri: String
)