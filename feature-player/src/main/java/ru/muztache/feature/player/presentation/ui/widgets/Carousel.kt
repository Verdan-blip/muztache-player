package ru.muztache.feature.player.presentation.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.pow

@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    itemsCount: Int,
    onItemSelected: (index: Int) -> Unit = { },
    overshootFraction: Float = 0.5f,
    itemSpacing: Dp = 0.dp,
    state: CarouselState = rememberCarouselState(),
    itemContent: @Composable (index: Int) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val measurePolicy = rememberCarouselMeasurePolicy(
        state = state,
        itemSpacing = itemSpacing,
        overshootFraction = overshootFraction
    )

    Layout(
        content = {
            CarouselContent(
                itemsCount = itemsCount,
                overshootFraction = overshootFraction,
                itemSpacing = itemSpacing,
                state = state
            ) { index ->
                itemContent(index)
            }
        },
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        state.onDrag(dragAmount.x.toInt())
                    },
                    onDragEnd = {
                        coroutineScope.launch {
                            state.flingToCurrentItemPosition()
                        }
                    }
                )
            }
            .then(modifier),
        measurePolicy = measurePolicy
    )

    LaunchedEffect(Unit) {
        state.setOnItemReselectListener(listener = onItemSelected)
    }
}

@Composable
fun CarouselContent(
    itemsCount: Int,
    itemSpacing: Dp,
    overshootFraction: Float,
    state: CarouselState,
    itemContent: @Composable (index: Int) -> Unit
) {
    val itemReductionFactor = remember { 0.75f }

    repeat(itemsCount) { index ->
        Box(
            modifier = Modifier
                .graphicsLayer {
                    val indexOffset = state.calculateIndexOffset(index)
                    val draggingOffset = state.calculateDraggingOffset(
                        overshootFraction, itemSpacing.roundToPx()
                    )
                    val generalOffset = (indexOffset - draggingOffset / 2).absoluteValue

                    val startOffset = generalOffset.coerceAtLeast(0f)
                    val endOffset = generalOffset.coerceAtMost(0f)

                    scaleX = itemReductionFactor.pow(startOffset - endOffset)
                    scaleY = itemReductionFactor.pow(startOffset - endOffset)
                }
        ) {
            itemContent(index)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CarouselPreview() {

    val carouselState = rememberCarouselState()

    Carousel(
        itemSpacing = 4.dp,
        itemsCount = 4,
        state = carouselState
    ) {
        Image(
            painter = painterResource(
                id = ru.muztache.core.theme.R.drawable.small_cover_example
            ),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .clip(RoundedCornerShape(size = 24.dp))
        )
    }
}