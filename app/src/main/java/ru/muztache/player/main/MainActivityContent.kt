package ru.muztache.player.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import ru.muztache.core.common.base.entity.BaseAudioItemModel
import ru.muztache.feature.player.presentation.ui.PlayerScreen
import ru.muztache.player.main.mvi.MainActivityViewModel

val items = listOf(
    BaseAudioItemModel(
        id = 0,
        title = "Blinding Lights",
        authors = listOf("The Weeknd"),
        audioUri = "https://cdn18.deliciouspeaches.com/get/music/20191204/muzlome_The_Weeknd_-_Blinding_Lights_67509023.mp3",
        coverUri = "https://upload.wikimedia.org/wikipedia/ru/7/75/Blinding_Lights.jpg"
    ),
    BaseAudioItemModel(
        id = 1,
        title = "Прощание",
        authors = listOf("Три дня дождя", "Polnalubvi"),
        audioUri = "https://d11.drivemusic.me/dl/jufrXLq3FVmLY44CcDYfTQ/1721721313/download_music/2023/10/tri-dnja-dozhdja-feat.-mona-proshhanie.mp3",
        coverUri = "https://cdns-images.dzcdn.net/images/cover/821a2b12a891df3bb6ee7ed29ffd979a/1900x1900-000000-80-0-0.jpg"
    ),
    BaseAudioItemModel(
        id = 2,
        title = "Демоны",
        authors = listOf("Три дня дождя"),
        audioUri = "https://s.muzrecord.com/files/tri-dnya-dozhdya-demony.mp3",
        coverUri = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8yjms6xZWrJLUWwjwU-RL7FsXgCocfRc35Q&s"
    ),
)

@Composable
fun MainActivityContent(viewModel: MainActivityViewModel) {
    Navigator(screen = PlayerScreen())
}

@Preview(showSystemUi = true)
@Composable
private fun MainActivityContentPreview() {
    Navigator(screen = PlayerScreen())
}