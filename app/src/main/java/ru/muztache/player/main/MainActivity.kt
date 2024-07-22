package ru.muztache.player.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.muztache.player.ui.theme.MuztacheplayerTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MuztacheplayerTheme {
                //Navigator(PlayerScreen())
                MainActivityContent(viewModel = viewModel)
            }
        }
    }
}