package ru.muztache.core.theme.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import ru.muztache.core.theme.R
import ru.muztache.core.theme.ui.colors.Surface

data class TrackCardModel(
    val id: Long,
    val title: String,
    val artists: List<String>,
    val smallCoverUri: String
)

@Composable
fun TrackCard(track: TrackCardModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Surface,
                shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.small_card_corner_radius)
                )
            )
            .padding(
                dimensionResource(id = R.dimen.small_card_padding)
            )
    ) {
        SubcomposeAsyncImage(
            model = track.smallCoverUri,
            contentDescription = stringResource(
                id = R.string.small_cover_content_description
            ),
            loading = {
                CircularProgressIndicator()
            },
            modifier = Modifier
                .size(
                    dimensionResource(id = R.dimen.small_cover_size)
                )
                .clip(
                    shape = RoundedCornerShape(
                        size = dimensionResource(
                            id = R.dimen.small_cover_corner_radius
                        )
                    )
                ),

            )
        Column(
            modifier = Modifier
                .padding(
                    start = dimensionResource(
                        id = R.dimen.small_card_track_info_padding_left
                    )
                )
        ) {
            LightText(
                text = track.title,
                modifier = Modifier.padding(
                    bottom = dimensionResource(
                        id = R.dimen.small_card_track_info_text_padding_bottom
                    )
                )
            )
            LightText(
                text = track.artists.joinToString(
                    separator = stringResource(
                        id = R.string.separator_between_artists
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun TrackCardPreview() {
    TrackCard(
        track = TrackCardModel(
            id = 0,
            title = "Blinding Eyes",
            listOf("The Weeknd"),
            smallCoverUri = "http://example.ru"
        )
    )
}