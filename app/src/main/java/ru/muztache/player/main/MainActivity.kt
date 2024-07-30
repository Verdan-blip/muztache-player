package ru.muztache.player.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.muztache.player.ui.theme.MuztachePlayerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MuztachePlayerTheme {
                val viewModel: MainActivityViewModel by viewModel()
                MainActivityContent(viewModel = viewModel)
            }
        }
    }
}