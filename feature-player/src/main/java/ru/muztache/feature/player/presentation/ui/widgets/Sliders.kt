package ru.muztache.feature.player.presentation.ui.widgets

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import ru.muztache.feature.player.R
import ru.muztache.feature.player.presentation.ui.theme.PlayingSliderActiveTrackColor
import ru.muztache.feature.player.presentation.ui.theme.PlayingSliderInactiveTrackColor
import ru.muztache.feature.player.presentation.ui.theme.PlayingSliderThumbColor

@Composable
fun PlayingProgressSlider(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    onSeeking: (progress: Float) -> Unit,
    onSeekingFinished: () -> Unit
) {
    Slider(
        modifier = modifier
            .width(
                width = dimensionResource(
                    id = R.dimen.playing_progress_slider_width
                )
            ),
        value = progress,
        onValueChange = { sliderProgress -> onSeeking(sliderProgress) },
        onValueChangeFinished = { onSeekingFinished() },
        colors = SliderDefaults.colors(
            thumbColor = PlayingSliderThumbColor,
            activeTrackColor = PlayingSliderActiveTrackColor,
            inactiveTrackColor = PlayingSliderInactiveTrackColor
        )
    )
}