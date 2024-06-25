package ru.muztache.feature.feed.presentation.mapper

import ru.muztache.core.theme.ui.widgets.TrackCardModel
import ru.muztache.feature.feed.presentation.entity.TrackModel

fun TrackModel.toTrackCardModel(): TrackCardModel =
    TrackCardModel(
        id = id,
        title = title,
        artists = artists,
        smallCoverUri = smallCoverUri
    )