package ru.muztache.core.theme.ui.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LightText(
    modifier: Modifier = Modifier,
    text: String = "Hello",
    color: Color = Color.White
) {
    Text(
        text = text,
        color = color,
        fontWeight = FontWeight.Light,
        modifier = modifier
    )
}

