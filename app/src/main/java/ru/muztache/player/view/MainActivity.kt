package ru.muztache.player.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import ru.muztache.feature.player.presentation.ui.PlayerScreen
import ru.muztache.player.ui.theme.MuztacheplayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuztacheplayerTheme {
                Navigator(PlayerScreen())
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainActivityContent() {
    Navigator(screen = PlayerScreen())
}