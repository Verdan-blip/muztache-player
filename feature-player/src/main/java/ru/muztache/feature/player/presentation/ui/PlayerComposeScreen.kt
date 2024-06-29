package ru.muztache.feature.player.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.muztache.feature.player.R
import ru.muztache.feature.player.presentation.mvi.PlayerAction
import ru.muztache.feature.player.presentation.mvi.PlayerViewModel
import ru.muztache.feature.player.presentation.mvi.UiState
import ru.muztache.feature.player.presentation.ui.theme.BackgroundBlurRadius
import ru.muztache.feature.player.presentation.ui.widgets.ArtistsText
import ru.muztache.feature.player.presentation.ui.widgets.DurationAndProgressText
import ru.muztache.feature.player.presentation.ui.widgets.NextMusicImage
import ru.muztache.feature.player.presentation.ui.widgets.PlayPauseImage
import ru.muztache.feature.player.presentation.ui.widgets.PlayingProgressSlider
import ru.muztache.feature.player.presentation.ui.widgets.PreviousMusicImage
import ru.muztache.feature.player.presentation.ui.widgets.TitleText


@Composable
private fun PlayerComposeScreenContent(
    uiState: UiState,
    onSeeking: (progress: Float) -> Unit,
    onSeekTo: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    BlurredImageBackground(
        painter = painterResource(
            id = ru.muztache.core.theme.R.drawable.small_cover_example
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.cover_padding_top)
                    )
                    .size(
                        size = dimensionResource(id = R.dimen.cover_size)
                    )
                    .clip(
                        shape = RoundedCornerShape(
                            size = dimensionResource(
                                id = R.dimen.cover_corner_radius
                            )
                        )
                    )
                    .align(Alignment.CenterHorizontally),
                contentDescription = stringResource(
                    id = R.string.cover_content_description
                ),
                painter = painterResource(
                    id = ru.muztache.core.theme.R.drawable.small_cover_example
                )
            )
            TitleText(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.title_text_padding_top)
                    )
                    .align(Alignment.CenterHorizontally),
                title = uiState.title
            )
            ArtistsText(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(
                            id = R.dimen.artists_text_padding_top
                        )
                    )
                    .align(Alignment.CenterHorizontally),
                artists = uiState.artists
            )
            PlayingProgressSlider(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(
                            id = R.dimen.playing_progress_slider_padding_top
                        )
                    )
                    .align(Alignment.CenterHorizontally),
                progress = uiState.progress,
                onSeeking = onSeeking,
                onSeekingFinished = onSeekTo
            )
            DurationAndProgressText(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(
                            id = R.dimen.progress_and_duration_text_padding_top
                        )
                    )
                    .align(Alignment.CenterHorizontally),
                formattedDuration = uiState.formattedDuration,
                formattedProgress = uiState.formattedProgress
            )
            Row(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(
                            id = R.dimen.control_panel_padding_top
                        )
                    )
                    .align(Alignment.CenterHorizontally)
            ) {
                PreviousMusicImage(
                    modifier = Modifier
                        .padding(
                            end = dimensionResource(
                                id = R.dimen.previous_button_padding_end
                            )
                        )
                        .align(Alignment.CenterVertically),
                    onClick = onPrevious
                )
                PlayPauseImage(
                    modifier = Modifier
                        .size(
                            size = dimensionResource(
                                id = R.dimen.play_pause_button_size
                            )
                        ),
                    onClick = onPlayPause,
                )
                NextMusicImage(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(
                                id = R.dimen.next_button_padding_start
                            )
                        )
                        .align(Alignment.CenterVertically),
                    onClick = onNext
                )
            }
        }
    }
}

@Composable
fun BlurredImageBackground(
    modifier: Modifier = Modifier,
    painter: Painter,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(
                    radius = dimensionResource(
                        id = R.dimen.background_image_blur_radius
                    )
                )
                .alpha(BackgroundBlurRadius),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        content()
    }
}

@Composable
fun PlayerComposeScreen(viewModel: PlayerViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PlayerComposeScreenContent(
        uiState = uiState,
        onSeeking = { progress ->
            viewModel.onAction(PlayerAction.Seeking(progress))
        },
        onSeekTo = {
            viewModel.onAction(PlayerAction.SeekingFinished)
        },
        onPlayPause = { viewModel.onAction(PlayerAction.PlayPause) },
        onNext = { viewModel.onAction(PlayerAction.Next) },
        onPrevious = { viewModel.onAction(PlayerAction.Previous) }
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PlayerComposeScreenPreview() {
    PlayerComposeScreen(viewModel = PlayerViewModel())
}