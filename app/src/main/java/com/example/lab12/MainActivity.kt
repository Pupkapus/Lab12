package com.example.lab12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

interface JsonDownloadCallback {
    fun onJsonDownloadComplete(result: Boolean)
}

class MainActivity : AppCompatActivity(), JsonDownloadCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonDownloader = JsonDownloader(this@MainActivity, this)
        jsonDownloader.downloadJsonFile("https://rickandmortyapi.com/api/character")
    }

    override fun onJsonDownloadComplete(result: Boolean) {
        val jsonReader = JsonReader(this@MainActivity)
        if(jsonReader != null) {
            DataCharacters = jsonReader.readCharacterData()
            val characters = DataCharacters
            if (characters != null) {
                Log.d("Tag1", characters.size.toString())
            }
        }
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
    }

}