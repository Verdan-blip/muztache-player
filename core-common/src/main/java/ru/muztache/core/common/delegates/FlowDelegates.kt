package ru.muztache.core.common.delegates

import kotlinx.coroutines.flow.StateFlow
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class state <T, V> (
    private val stateFlow: StateFlow<V>
) : ReadOnlyProperty<T, V> {

    override fun getValue(thisRef: T, property: KProperty<*>): V = stateFlow.value
}