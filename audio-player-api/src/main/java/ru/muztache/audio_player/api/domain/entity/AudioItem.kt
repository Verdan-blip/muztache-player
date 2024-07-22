package ru.muztache.audio_player.api.domain.entity

data class AudioItem(
    val id: Long,
    val title: String,
    val authors: List<String>,
    val audioUri: String,
    val coverUri: String
)