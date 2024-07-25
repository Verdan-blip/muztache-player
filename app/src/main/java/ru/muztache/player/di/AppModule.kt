package ru.muztache.player.di

import org.koin.dsl.module
import ru.muztache.core.common.contracts.ResourceManager
import ru.muztache.player.contracts.ResourceManagerImpl

val appModule = module {
    factory<ResourceManager> { ResourceManagerImpl(get()) }
}