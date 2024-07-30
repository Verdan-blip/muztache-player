package ru.muztache.audio_player.impl.presentation.mapper

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import ru.muztache.audio_player.api.domain.entity.AudioItem
import ru.muztache.audio_player.api.domain.entity.AudioItemInfo
import ru.muztache.core.common.contracts.ResourceManager
import ru.muztache.core.theme.R
import ru.muztache.core.util.time.Milliseconds

class MediaItemMappingException(message: String) : Exception(message)

fun AudioItem.toMediaItem(resourceManager: ResourceManager): MediaItem {
    return with(resourceManager) {
        MediaItem.Builder()
            .setUri(audioUri)
            .setMediaId(id.toString())
            .setMediaMetadata(MediaMetadata.Builder()
                .setArtist(artists.joinToString(separator = getString(R.string.separator_between_artists)))
                .setTitle(title)
                .setArtworkUri(coverUri.toUri())
                .build())
            .build()
    }
}

fun List<AudioItem>.toMediaItemList(resourceManager: ResourceManager): List<MediaItem> {
    return map { audioItem ->
        audioItem.toMediaItem(resourceManager)
    }
}

fun MediaItem.toAudioItemInfo(
    resourceManager: ResourceManager,
    player: Player
): AudioItemInfo {
    return with(resourceManager) {
        AudioItemInfo(
            id = mediaId.toLong(),
            title = mediaMetadata.title?.toString()
                ?: throw MediaItemMappingException("Missing title"),
            artists = mediaMetadata.artist?.toString()?.split(getString(R.string.separator_between_artists))
                ?: throw MediaItemMappingException("Missing artists"),
            audioUri = localConfiguration?.uri?.toString()
                ?: throw MediaItemMappingException("Missing audio uri"),
            coverUri = mediaMetadata.artworkUri?.toString()
                ?: throw MediaItemMappingException("Missing artwork uri"),
            duration = if (player.duration < 0)
                Milliseconds(value = 0)
            else
                Milliseconds(value = player.duration)
        )
    }
}

fun List<MediaItem>.toAudioItemInfoList(
    resourceManager: ResourceManager,
    player: Player
): List<AudioItemInfo> {
    return map { mediaItem ->
        mediaItem.toAudioItemInfo(resourceManager, player)
    }
}