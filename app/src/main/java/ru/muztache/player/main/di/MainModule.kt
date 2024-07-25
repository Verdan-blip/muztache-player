package ru.muztache.player.main.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.muztache.audio_player.api.domain.controller.AudioPlayerController
import ru.muztache.audio_player.impl.presentation.impl.AudioPlayerControllerImpl
import ru.muztache.player.main.MainActivityViewModel

const val NAME_AUDIO_PLAYER_CONTROLLER_PROXY = "audioPlayerControllerProxy"

val mainModule = module {
    factory<AudioPlayerController>() { AudioPlayerControllerImpl(get(), get()) }
    viewModel {
        MainActivityViewModel(audioPlayerController = get())
    }
}