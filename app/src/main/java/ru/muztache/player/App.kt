package ru.muztache.player

import android.app.Application
import android.content.Intent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.muztache.audio_player.impl.di.audioPlayerModule
import ru.muztache.core.common.contracts.ActivityStarter
import ru.muztache.player.main.MainActivity
import ru.muztache.player.main.di.mainModule

class App : Application(), ActivityStarter {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)

            //Main
            modules(mainModule)

            //Specific modules
            modules(audioPlayerModule)

            //Feature modules
        }
    }

    override fun getIntentForMainActivity(): Intent = Intent(this, MainActivity::class.java)
}