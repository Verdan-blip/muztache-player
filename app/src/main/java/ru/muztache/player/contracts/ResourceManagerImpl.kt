package ru.muztache.player.contracts

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import ru.muztache.core.common.contracts.ResourceManager

class ResourceManagerImpl(
    private val context: Context
) : ResourceManager {

    override fun getString(id: Int): String =
        context.getString(id)

    override fun getString(id: Int, vararg formatArgs: Any): String =
        context.getString(id, formatArgs)

    override fun getQuantityString(id: Int, quantity: Int): String =
        context.resources.getQuantityString(id, quantity)

    override fun getInteger(id: Int): Int =
        context.resources.getInteger(id)

    override fun getColor(id: Int): Int =
        ContextCompat.getColor(context, id)

    override fun getDrawable(id: Int): Drawable? =
        ContextCompat.getDrawable(context, id)

    override fun getDimension(id: Int): Float =
        context.resources.getDimension(id)
}