package ru.muztache.core.common.contracts

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface ResourceManager {


    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, vararg formatArgs: Any): String

    fun getQuantityString(@PluralsRes id: Int, quantity: Int): String

    fun getInteger(@IntegerRes id: Int): Int

    fun getColor(@ColorRes id: Int): Int

    fun getDrawable(@DrawableRes id: Int): Drawable?

    fun getDimension(@DimenRes id: Int): Float
}