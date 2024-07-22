package ru.muztache.feature.feed.presentation.view

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.muztache.core.common.base.viewmodel.BaseViewModel
import ru.muztache.feature.feed.presentation.entity.TrackModel

class FeedViewModel : BaseViewModel() {

    private val _screenState = MutableStateFlow(ScreenState())

    val screenState: StateFlow<ScreenState>
        get() = _screenState

    fun onAction(action: Action) {

    }
}

data class ScreenState(
    val chartTracksState: List<TrackModel>? = null
)

sealed interface Action {

}