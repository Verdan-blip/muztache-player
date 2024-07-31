package ru.muztache.feature.player.presentation.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import ru.muztache.feature.player.presentation.mvi.PlayerViewModel

class PlayerScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = viewModel<PlayerViewModel>()
    }
}