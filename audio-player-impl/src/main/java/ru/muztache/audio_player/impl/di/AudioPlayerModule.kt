package ru.muztache.audio_player.impl.di

import org.koin.dsl.module
import ru.muztache.audio_player.api.domain.controller.AudioPlayerController
import ru.muztache.audio_player.impl.presentation.impl.AudioPlayerControllerImpl

val audioPlayerModule = module {
    single<AudioPlayerController> { AudioPlayerControllerImpl(get(), get()) }
}