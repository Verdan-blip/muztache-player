package ru.muztache.feature.player.presentation.ui.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

interface CarouselState {

    val xOffsetState: IntState

    val currentItemInfoState: State<ItemInfo>

    fun onDrag(xOffset: Int)

    fun onCurrentItemInfoChange(newItemInfo: ItemInfo)

    suspend fun flingToCurrentItemPosition()

    data class ItemInfo(
        val index: Int,
        val position: Int,
        val width: Int
    )
}

class CarouselStateImpl : CarouselState {

    private val _xOffsetState = mutableIntStateOf(0)
    override val xOffsetState: IntState
        get() = _xOffsetState

    private val _currentItemInfoState = mutableStateOf(CarouselState.ItemInfo(0, 0, 1))
    override val currentItemInfoState: State<CarouselState.ItemInfo>
        get() = _currentItemInfoState

    override fun onDrag(xOffset: Int) {
        _xOffsetState.intValue -= xOffset
    }

    override fun onCurrentItemInfoChange(newItemInfo: CarouselState.ItemInfo) {
        _currentItemInfoState.value = newItemInfo
    }

    override suspend fun flingToCurrentItemPosition() {
        val animatable = Animatable(_xOffsetState.intValue.toFloat())
        animatable.animateTo(_currentItemInfoState.value.position.toFloat()) {
            _xOffsetState.intValue = asState().value.toInt()
        }
    }
}

@Composable
fun rememberCarouselState(): CarouselState {
    return remember { CarouselStateImpl() }
}

fun CarouselState.calculateDraggingOffset(
    overShootFraction: Float,
    itemSpacing: Int
): Float {
    val offset = xOffsetState.intValue
    val position = currentItemInfoState.value.position
    val width = currentItemInfoState.value.width
    return (offset - position).toFloat() / ((width + itemSpacing) * overShootFraction)
}

fun CarouselState.calculateIndexOffset(index: Int): Int {
    return index - currentItemInfoState.value.index
}