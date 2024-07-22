package ru.muztache.core.common.tools.sdk

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast


inline fun runOnApi33(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        block()
    }
}