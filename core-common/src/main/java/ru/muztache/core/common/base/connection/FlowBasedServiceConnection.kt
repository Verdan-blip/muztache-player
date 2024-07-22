package ru.muztache.core.common.base.connection

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.muztache.core.common.coroutines.scopes.globalScope

class FlowBasedServiceConnection <T : IBinder> (
    private val isRequiredBinder: (IBinder) -> Boolean
) : ServiceConnection {

    private val _binderEvent = MutableSharedFlow<T>()

    private val _errorCallback = MutableSharedFlow<Callback>()
    val errorCallback: SharedFlow<Callback>
        get() = _errorCallback

    @Suppress("UNCHECKED_CAST")
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        service?.also { binder ->
            if (isRequiredBinder(binder)) {
                globalScope.launch { _binderEvent.emit(binder as T) }
            }
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        globalScope.launch { _errorCallback.emit(Callback.Disconnected) }
    }

    override fun onBindingDied(name: ComponentName?) {
        globalScope.launch { _errorCallback.emit(Callback.BindingDied) }
    }

    suspend fun awaitBinder(): T {
        return _binderEvent.first()
    }

    sealed interface Callback {

        data object Disconnected : Callback

        data object BindingDied : Callback
    }
}