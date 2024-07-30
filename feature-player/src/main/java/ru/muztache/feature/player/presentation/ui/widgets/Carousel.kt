package ru.muztache.feature.player.presentation.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.muztache.core.util.extensions.between
import kotlin.math.absoluteValue
import kotlin.math.pow

@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    itemsCount: Int,
    overshootFraction: Float = 0.5f,
    itemSpacing: Dp = 0.dp,
    state: CarouselState,
    itemContent: @Composable (index: Int) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

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
        modifier = modifier
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
    ) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val firstPlaceableSemiWidth = placeables.first().width / 2
        val centerOffset = constraints.maxWidth / 2
        val dragOffsetValue = state.xOffsetState.intValue

        layout(constraints.maxWidth, constraints.maxHeight) {

            var currentItemPositionLocal = centerOffset - firstPlaceableSemiWidth
            var currentItemPosition = centerOffset - dragOffsetValue - firstPlaceableSemiWidth

            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(x = currentItemPosition, y = 0)

                //Fling behavior
                if (isCandidateToBeCurrentItem(
                        currentItemPosition,
                        placeable,
                        itemSpacing.roundToPx(),
                        centerOffset,
                        overshootFraction
                    )
                ) {
                    state.onCurrentItemInfoChange(
                        CarouselState.ItemInfo(
                            index = index,
                            position = currentItemPositionLocal - centerOffset + firstPlaceableSemiWidth,
                            width = placeable.width
                        )
                    )
                }

                currentItemPosition += placeable.width + itemSpacing.roundToPx()
                currentItemPositionLocal += placeable.width + itemSpacing.roundToPx()
            }
        }
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

private fun isCandidateToBeCurrentItem(
    xPosition: Int,
    suspect: Placeable,
    itemSpacing: Int,
    centerOffset: Int,
    overshootFraction: Float
): Boolean {
    return (xPosition + suspect.width / 2).between(
        left = -(suspect.width + itemSpacing) * overshootFraction + centerOffset,
        right = (suspect.width + itemSpacing) * overshootFraction + centerOffset
    )
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