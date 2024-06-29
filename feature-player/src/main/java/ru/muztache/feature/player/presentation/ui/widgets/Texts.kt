package ru.muztache.feature.player.presentation.ui.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.muztache.core.common.time.toMmSsString
import ru.muztache.core.theme.ui.widgets.fontDimensionResource
import ru.muztache.feature.player.R

@Composable
fun TitleText(
    title: String?,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = title ?: stringResource(
            id = R.string.title_text_placeholder
        ),
        color = Color.White,
        fontWeight = FontWeight.W200,
        fontSize = dimensionResource(
            id = R.dimen.title_text_size
        ).value.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ArtistsText(
    artists: List<String>,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = artists.joinToString(
            separator = stringResource(
                id = ru.muztache.core.theme.R.string.separator_between_artists
            )
        ),
        color = Color.White,
        fontWeight = FontWeight.Light,
        fontSize = fontDimensionResource(
            id = R.dimen.artists_text_size
        ),
        textAlign = TextAlign.Center
    )
}


@Composable
fun DurationAndProgressText(
    formattedDuration: String?,
    formattedProgress: String?,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        color = Color.White,
        fontWeight = FontWeight.W200,
        fontSize = fontDimensionResource(
            id = R.dimen.progress_and_duration_text_size
        ),
        textAlign = TextAlign.Center,
        text = stringResource(
            id = R.string.progress_and_duration_text_format,
            formattedProgress ?: stringResource(id = R.string.progress_text_placeholder),
            formattedDuration ?: stringResource(id = R.string.duration_text_placeholder)
        )
    )
}