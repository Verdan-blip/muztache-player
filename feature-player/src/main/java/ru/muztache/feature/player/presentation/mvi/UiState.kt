package ru.muztache.feature.player.presentation.mvi

data class UiState(
    val title: String? = null,
    val artists: List<String> = listOf(),
    val coverUri: String? = null,
    val progress: Float = 0f,
    val formattedProgress: String? = null,
    val formattedDuration: String? = null
)