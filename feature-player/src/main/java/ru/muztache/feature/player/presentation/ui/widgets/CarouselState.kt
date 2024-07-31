package ru.muztache.feature.player.presentation.ui.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

typealias OnItemReselectListener = (index: Int) -> Unit

interface CarouselState {

    val xOffset: IntState

    val currentItemInfo: State<ItemInfo>

    fun onDrag(xOffset: Int)

    fun changeSelectedItem(itemInfo: ItemInfo)

    suspend fun flingToCurrentItemPosition()

    fun setOnItemReselectListener(listener: OnItemReselectListener)

    data class ItemInfo(
        val index: Int,
        val position: Int,
        val width: Int
    )
}

class CarouselStateImpl : CarouselState {

    private val _xOffsetState = mutableIntStateOf(0)
    override val xOffset: IntState
        get() = _xOffsetState

    private val _currentItemInfoState = mutableStateOf(CarouselState.ItemInfo(0, 0, 1))
    override val currentItemInfo: State<CarouselState.ItemInfo>
        get() = _currentItemInfoState

    private var onItemReselectListener: OnItemReselectListener = { }

    override fun onDrag(xOffset: Int) {
        _xOffsetState.intValue -= xOffset
    }

    override fun changeSelectedItem(itemInfo: CarouselState.ItemInfo, ) {
        LazyListState
        if (_currentItemInfoState.value != itemInfo) {
            _currentItemInfoState.value = itemInfo
            onItemReselectListener(itemInfo.index)
        }
    }

    override fun setOnItemReselectListener(listener: OnItemReselectListener) {
        onItemReselectListener = listener
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
    val offset = xOffset.intValue
    val position = currentItemInfo.value.position
    val width = currentItemInfo.value.width
    return (offset - position).toFloat() / ((width + itemSpacing) * overShootFraction)
}

fun CarouselState.calculateIndexOffset(index: Int): Int {
    return index - currentItemInfo.value.index
}