package ru.muztache.feature.player.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.muztache.feature.player.presentation.mvi.PlayerViewModel
import ru.muztache.navigation.SharedScreen

class PlayerScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = viewModel<PlayerViewModel>()
    }
}