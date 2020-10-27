package com.example.contentresolverex01

import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_item.view.*
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    var listData = mutableListOf<Music>()
    var mediaPlayer: MediaPlayer? = null

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                if (mediaPlayer != null) {

                    if(mediaPlayer!!.isPlaying){
                        mediaPlayer?.stop()
                    }

                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                else {

                    mediaPlayer = MediaPlayer.create(itemView.context, musicUri)

                        mediaPlayer?.start()
                }
            }
        }

        var musicUri: Uri? = null
        fun setMusic(music: Music) {
            itemView.imageAlbum.setImageURI(music.getAlbumUri())
            itemView.textArtist.text = music.artist
            itemView.textTitle.text = music.title
            val duration = SimpleDateFormat("mm:ss").format(music.duration)
            itemView.textDuration.text = duration

            musicUri = music.getMusicUri()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = listData.get(position)

        holder.setMusic(music)
    }

    override fun getItemCount(): Int {
        return listData.count()
    }
}