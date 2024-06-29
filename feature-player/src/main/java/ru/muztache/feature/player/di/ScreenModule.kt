package ru.muztache.feature.player.di

import cafe.adriel.voyager.core.registry.screenModule
import ru.muztache.feature.player.presentation.ui.PlayerScreen
import ru.muztache.feature.player.presentation.ui.PlayerScreenProvider

val featurePlayerScreenModule = screenModule {
    register<PlayerScreenProvider> {
        PlayerScreen()
    }
}