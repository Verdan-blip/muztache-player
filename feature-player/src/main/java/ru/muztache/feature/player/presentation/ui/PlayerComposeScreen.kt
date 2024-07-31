package ru.muztache.feature.player.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.muztache.core.theme.ui.theme.MuztacheTheme
import ru.muztache.core.theme.ui.theme.MuztacheThemeProvider
import ru.muztache.feature.player.R
import ru.muztache.feature.player.presentation.mvi.PlayerViewModel
import ru.muztache.feature.player.presentation.ui.widgets.Carousel


@Composable
fun PlayerComposeScreenContent(
    viewModel: PlayerViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.tab_play),
            style = MuztacheTheme.typography.toolbarText,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        Carousel(
            itemsCount = 3,
            modifier = Modifier
                .align(Alignment.Center)
        ) { index ->
            Image(
                painter = painterResource(id = ru.muztache.core.theme.R.drawable.small_cover_example),
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = ru.muztache.core.theme.R.dimen.size_extra_large))
                    .clip(RoundedCornerShape(size = dimensionResource(id = ru.muztache.core.theme.R.dimen.radius_extra_large_32)))
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PlayerComposeScreenPreview() {
    MuztacheThemeProvider(darkTheme = false) {
        PlayerComposeScreenContent(viewModel = PlayerViewModel())
    }
}