package ru.muztache.feature.feed.presentation.entity

class TrackModel(
    val id: Long,
    val title: String,
    val artists: List<String>,
    val smallCoverUri: String
)