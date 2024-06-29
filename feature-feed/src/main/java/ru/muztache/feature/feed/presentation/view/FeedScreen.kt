package ru.muztache.feature.feed.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.muztache.core.theme.ui.widgets.TrackCard
import ru.muztache.feature.feed.presentation.entity.TrackModel
import ru.muztache.feature.feed.presentation.mapper.toTrackCardModel


@Composable
fun FeedScreen(tracks: List<TrackModel>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(
                    id = ru.muztache.core.theme.R.dimen.space_between_small_cards
                )
            )
        ) {
            items(tracks) { track ->
                TrackCard(track = track.toTrackCardModel())
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FeedScreenPreview() {
    FeedScreen(tracks = listOf(
        TrackModel(
            id = 0,
            title = "Blinding Eyes",
            artists = listOf("The Weeknd"),
            smallCoverUri = "https://upload.wikimedia.org/wikipedia/en/e/e6/The_Weeknd_-_Blinding_Lights.png"
        ),
        TrackModel(
            id = 0,
            title = "Blinding Eyes",
            artists = listOf("The Weeknd"),
            smallCoverUri = "https://upload.wikimedia.org/wikipedia/en/e/e6/The_Weeknd_-_Blinding_Lights.png"
        )
    ))
}