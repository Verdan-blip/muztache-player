package ru.muztache.core.theme.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class MuztacheTypography(

    val title: TextStyle = TextStyle(

    ),

    val subtitleSmall: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W300
    ),
    val subtitleMedium: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400
    ),
    val subtitleLarge: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.W400
    ),

    val bodySmall: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W300
    ),
    val bodyMedium: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400
    ),
    val bodyLarge: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.W400
    ),

    val toolbarText: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.W700
    )
)

val LocalMuztacheTypography = compositionLocalOf<MuztacheTypography> {
    error("Typography has not been provided")
}