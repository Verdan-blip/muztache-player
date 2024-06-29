package ru.muztache.feature.player.presentation.entity

data class PlayableInfoModel(
    val id: Long,
    val title: String,
    val artists: List<String>,
    val cover: String,
    val progress: Long,
    val duration: Long,
    val isPlaying: Boolean
)