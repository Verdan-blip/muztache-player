package ru.muztache.player.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.muztache.core.common.base.entity.BaseAudioItemModel

@Composable
fun MainActivityContent(
    viewModel: MainActivityViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                //viewModel.start()
                viewModel.play(
                    BaseAudioItemModel(
                        id = 0,
                        title = "Blinding Lights",
                        authors = listOf("The Weeknd"),
                        audioUri = "https://cdn18.deliciouspeaches.com/get/music/20191204/muzlome_The_Weeknd_-_Blinding_Lights_67509023.mp3",
                        coverUri = "https://upload.wikimedia.org/wikipedia/ru/7/75/Blinding_Lights.jpg"
                    )
                )
            }
        ) {
            Text(text = "Start service")
        }
    }
}