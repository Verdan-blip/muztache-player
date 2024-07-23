package ru.muztache.audio_player.impl.presentation.service.session

import android.media.session.MediaSession
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import kotlinx.coroutines.launch
import ru.muztache.audio_player.impl.presentation.service.MediaSessionPlayerService
import ru.muztache.core.common.coroutines.scopes.globalScope
import ru.muztache.core.util.time.toMilliseconds

class MediaSessionCallbackImpl(
    private val mediaSessionPlayerService: MediaSessionPlayerService
) : MediaSession.Callback() {

    override fun onPlay() {
        Log.d("MEDIA_SESSION_CALLBACK", "onPlay")
        globalScope.launch { mediaSessionPlayerService.resume() }
    }

    override fun onPause() {
        Log.d("MEDIA_SESSION_CALLBACK", "onPause")
        globalScope.launch { mediaSessionPlayerService.pause() }
    }

    override fun onSkipToNext() {
        Log.d("MEDIA_SESSION_CALLBACK", "onSkipToNext")
        globalScope.launch { mediaSessionPlayerService.skipToNext() }
    }

    override fun onSkipToPrevious() {
        Log.d("MEDIA_SESSION_CALLBACK", "onSkipToPrevious")
        globalScope.launch { mediaSessionPlayerService.skipToPrevious() }
    }

    override fun onSeekTo(pos: Long) {
        Log.d("MEDIA_SESSION_CALLBACK", "onSeekTo $pos")
        globalScope.launch { mediaSessionPlayerService.seekTo(pos.toMilliseconds()) }
    }

    override fun onRewind() {
        Log.d("MEDIA_SESSION_CALLBACK", "onRewind")
        globalScope.launch { mediaSessionPlayerService.clearQueue() }
    }

    override fun onStop() {
        Log.d("MEDIA_SESSION_CALLBACK", "onStop")
        globalScope.launch { mediaSessionPlayerService.stop() }
    }
}