package ru.muztache.core.common.tools.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

class BitmapLoadingException(exception: Exception) : Exception(exception)

object BitmapUtils {

    suspend fun loadFromUri(uri: String): Bitmap {
        return try {
            withContext(Dispatchers.IO) {
                URL(uri).openStream()
            }.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        } catch (ex: IOException) {
            throw BitmapLoadingException(ex)
        }
    }
}