package ru.muztache.core.theme.ui.widgets

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun fontDimensionResource(@DimenRes id: Int): TextUnit =
    dimensionResource(id = id).value.sp