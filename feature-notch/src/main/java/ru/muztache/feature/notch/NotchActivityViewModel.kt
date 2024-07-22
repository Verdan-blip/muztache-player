package ru.muztache.feature.notch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.muztache.feature.notch.event.NotchActivityEvent

class NotchActivityViewModel : ViewModel() {

    private val _event = MutableSharedFlow<NotchActivityEvent>()
    val event: SharedFlow<NotchActivityEvent>
        get() = _event

    fun onPermissionGranted() {

    }

    fun onPermissionNotGranted() {

    }

    fun onStart() {
        viewModelScope.launch {
            _event.emit(NotchActivityEvent.RequestPermissionForOverlaying)
        }
    }
}