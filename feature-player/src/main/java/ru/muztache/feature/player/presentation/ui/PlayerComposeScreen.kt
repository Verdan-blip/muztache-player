package ru.muztache.feature.player.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.muztache.feature.player.presentation.mvi.PlayerViewModel


@Composable
fun PlayerComposeScreenContent(
    viewModel: PlayerViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = ru.muztache.core.theme.R.drawable.small_cover_example),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(256.dp)
                .clip(RoundedCornerShape(size = 24.dp))
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PlayerComposeScreenPreview() {
   PlayerComposeScreenContent(viewModel = PlayerViewModel())
}