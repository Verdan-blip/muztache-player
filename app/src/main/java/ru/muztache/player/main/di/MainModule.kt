package ru.muztache.player.main.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import ru.muztache.audio_player.api.domain.player.controller.AudioPlayerController
import ru.muztache.player.main.MainActivityViewModel
import ru.muztache.player.main.entity.AudioPlayerControllerProxy

const val NAME_AUDIO_PLAYER_CONTROLLER_PROXY = "audioPlayerControllerProxy"

val mainModule = module {
    factory<AudioPlayerController>(named(NAME_AUDIO_PLAYER_CONTROLLER_PROXY)) {
        AudioPlayerControllerProxy(get())
    }
    viewModel {
        MainActivityViewModel(
            audioPlayerController = get(
                qualifier = qualifier(
                    NAME_AUDIO_PLAYER_CONTROLLER_PROXY
                )
            )
        )
    }
}