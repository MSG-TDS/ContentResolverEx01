package com.example.contentresolverex01

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    val adapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermision()





        val listUrl = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        Log.d("x", listUrl.toString())

    }

    fun getMusicList(): List<Music> {
        val listUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val proj = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION,
        )

        val cursor = contentResolver.query(listUri,proj,null,null,null)
        val musicList = mutableListOf<Music>()
        while(cursor?.moveToNext()!!){
            val id = cursor.getString(0)
            val title = cursor.getString(1)
            val artist = cursor.getString(2)
            val albumId = cursor.getString(3)
            val duration = cursor.getLong(4)

            val music = Music(id, title, artist, albumId, duration)
            musicList.add(music)

        }

        return musicList

    }

    fun startProcess() {
        setContentView(R.layout.activity_main)

        adapter.listData.addAll(getMusicList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun checkPermision() {
        if (ContextCompat.checkSelfPermission(
                this,
                permissions[0]
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, 99)
        } else {
            startProcess()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        var check = true
        if (requestCode == 99) {

            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    check = false
                    break
                }
            }
        }
        if (!check) {
            Toast.makeText(this, "권한이 필요합니다.", Toast.LENGTH_SHORT)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}