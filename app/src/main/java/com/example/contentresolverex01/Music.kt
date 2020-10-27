package com.example.contentresolverex01

import android.media.MediaScannerConnection
import android.net.Uri
import android.provider.MediaStore

class Music(id: String, title: String?, artist: String?, albulId: String?, duration: Long?) {
    var id = ""
    var title: String? = null
    var artist: String? = null
    var albumId: String? = null
    var duration: Long? = null

    init {
        this.id = id
        this.title = title
        this.artist = artist
        this.albumId = albulId
        this.duration = duration
    }

    fun getMusicUri(): Uri {
        return Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
    }

    fun getAlbumUri(): Uri {
        return Uri.parse("content://media/external/audio/albumart/" + albumId)
    }
}