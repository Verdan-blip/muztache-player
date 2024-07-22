package ru.muztache.core.util.extensions

inline fun <T> T?.foldNullability(
    onNull: () -> Unit,
    onNotNull: (T) -> Unit
) {
    if (this == null)
        onNull()
    else
        onNotNull(this)
}