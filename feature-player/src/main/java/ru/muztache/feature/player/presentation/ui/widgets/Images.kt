package ru.muztache.feature.player.presentation.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.muztache.feature.player.R

@Composable
fun PlayPauseImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        modifier = modifier
            .clickable { onClick() },
        painter = painterResource(
            id = R.drawable.player_play
        ),
        contentDescription = stringResource(
            id = R.string.play_pause_content_description
        )
    )
}

@Composable
fun PreviousMusicImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        modifier = modifier
            .width(
                width = dimensionResource(
                    id = R.dimen.previous_button_width
                )
            )
            .height(
                height = dimensionResource(
                    id = R.dimen.previous_button_height
                )
            )
            .clickable { onClick() },
        painter = painterResource(
            id = R.drawable.player_previous
        ),
        contentDescription = stringResource(
            id = R.string.previous_content_description
        )
    )
}


@Composable
fun NextMusicImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        modifier = modifier
            .width(
                width = dimensionResource(
                    id = R.dimen.next_button_width
                )
            )
            .height(
                height = dimensionResource(
                    id = R.dimen.next_button_height
                )
            )
            .clickable { onClick() },
        painter = painterResource(
            id = R.drawable.player_next
        ),
        contentDescription = stringResource(
            id = R.string.next_content_description
        )
    )
}