package ru.muztache.player.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.muztache.core.theme.ui.theme.MuztacheTheme
import ru.muztache.player.main.mvi.MainActivityViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MuztacheTheme {
                val viewModel: MainActivityViewModel by viewModel()
                MainActivityContent(viewModel = viewModel)
            }
        }
    }
}