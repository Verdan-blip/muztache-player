package ru.muztache.feature.player.presentation.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import ru.muztache.core.util.extensions.between
import kotlin.math.max

class CarouselMeasurePolicyImpl(
    private val state: CarouselState,
    private val itemSpacing: Dp,
    private val overshootFraction: Float
) : MeasurePolicy {

    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {

        var maxHeight = constraints.minHeight
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints).apply { maxHeight = max(maxHeight, height) }
        }

        val firstPlaceableSemiWidth = placeables.first().width / 2
        val centerOffset = constraints.maxWidth / 2
        val dragOffsetValue = state.xOffset.intValue

        return layout(constraints.maxWidth, maxHeight) {

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
                    state.changeSelectedItem(
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

@Composable
fun rememberCarouselMeasurePolicy(
    state: CarouselState,
    itemSpacing: Dp,
    overshootFraction: Float
): MeasurePolicy =
    remember {
        CarouselMeasurePolicyImpl(state, itemSpacing, overshootFraction)
    }