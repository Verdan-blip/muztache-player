package ru.muztache.audio_player.impl.presentation.service.tools.utils

import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.entity.AudioItemInfo
import ru.muztache.core.util.time.Milliseconds

fun AudioItem.toAudioItemInfo(duration: Milliseconds): AudioItemInfo =
    AudioItemInfo(
        id = id,
        title = title,
        artists = authors,
        audioUri = audioUri,
        coverUri = coverUri,
        duration = duration
    )