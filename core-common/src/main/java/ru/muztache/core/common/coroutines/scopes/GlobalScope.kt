package ru.muztache.core.common.coroutines.scopes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val globalScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)