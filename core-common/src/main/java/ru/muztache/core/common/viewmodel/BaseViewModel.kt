package ru.muztache.core.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.muztache.core.common.event.DialogEvent
import ru.muztache.core.common.event.ToastEvent

abstract class BaseViewModel : ViewModel() {

    private val _dialogEvent = MutableSharedFlow<DialogEvent>()

    val dialogEvent: SharedFlow<DialogEvent>
        get() = _dialogEvent


    private val _toastEvent = MutableSharedFlow<ToastEvent>()

    val toastEvent: SharedFlow<ToastEvent>
        get() = _toastEvent


    protected fun emitDialogEvent(event: DialogEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            _dialogEvent.emit(event)
        }
    }

    protected fun emitToastEvent(event: ToastEvent) {
        viewModelScope.launch {
            _toastEvent.emit(event)
        }
    }

}