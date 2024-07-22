package ru.muztache.core.common.base.entity

class BaseAudioItemModel(
    val id: Long,
    val title: String,
    val authors: List<String>,
    val coverUri: String,
    val audioUri: String
)