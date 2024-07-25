package ru.muztache.player.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.muztache.core.common.base.entity.BaseAudioItemModel

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
fun MainActivityContent(
    viewModel: MainActivityViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            ControlButton(
                onClick = {
                    viewModel.play()
                },
                text = "Play"
            )
            ControlButton(
                onClick = {
                    viewModel.pause()
                },
                text = "Pause"
            )
            ControlButton(
                onClick = {
                    viewModel.skipToNext()
                },
                text = "Skip to next"
            )
            ControlButton(
                onClick = {
                    viewModel.skipToPrevious()
                },
                text = "Seek to previous"
            )
            ControlButton(
                onClick = {
                    viewModel.play(items)
                },
                text = "Play items"
            )
            ControlButton(
                onClick = {
                    viewModel.play(items.first())
                },
                text = "Play item"
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MainActivityContentPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            ControlButton(onClick = { }, text = "Start service")
            ControlButton(onClick = { }, text = "Play")
            ControlButton(onClick = { }, text = "Pause")
            ControlButton(onClick = { }, text = "Seek to next")
            ControlButton(onClick = { }, text = "Seek to previous")
            ControlButton(onClick = { }, text = "Play items")
        }
    }
}

@Composable
fun ControlButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick
    ) {
        Text(text = text)
    }
}