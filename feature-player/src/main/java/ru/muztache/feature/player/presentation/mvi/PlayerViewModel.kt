package ru.muztache.feature.player.presentation.mvi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.muztache.core.common.viewmodel.BaseViewModel
import ru.muztache.feature.player.presentation.mvi.PlayerAction
import ru.muztache.feature.player.presentation.mvi.UiState

class PlayerViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState>
        get() = _uiState

    fun onAction(action: PlayerAction) {

    }
}