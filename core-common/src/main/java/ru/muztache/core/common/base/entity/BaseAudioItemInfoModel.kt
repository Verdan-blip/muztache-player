package ru.muztache.core.common.base.entity

import ru.muztache.core.util.time.Milliseconds

class BaseAudioItemInfoModel(
    val id: Long,
    val title: String,
    val authors: List<String>,
    val coverUri: String,
    val audioUri: String,
    val duration: Milliseconds
)