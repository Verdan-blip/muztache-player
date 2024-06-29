package ru.muztache.player

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import ru.muztache.feature.player.di.featurePlayerScreenModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenRegistry {
            featurePlayerScreenModule()
        }
    }
}