package ru.muztache.audio_player.impl.di

import org.koin.dsl.module
import ru.muztache.audio_player.api.domain.player.controller.AudioPlayerController
import ru.muztache.audio_player.impl.presentation.impl.controller.MediaSessionPlayerController

val audioPlayerModule = module {
    single<AudioPlayerController> { MediaSessionPlayerController(get()) }
}