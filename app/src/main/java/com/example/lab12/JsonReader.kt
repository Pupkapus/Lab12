package com.example.lab12

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class JsonReader(private val context: Context) {
    private val TAG = "JsonReader"

    fun readCharacterData(): List<Character>? {
        try {
            val inputStream = context.openFileInput("character.json")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?

            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }

            val json = JSONObject(stringBuilder.toString())
            val results = json.getJSONArray("results")
            val characterList = mutableListOf<Character>()

            for (i in 0 until results.length()) {
                val character = results.getJSONObject(i)
                val id = character.getInt("id")
                val name = character.getString("name")
                val species = character.getString("species")
                val gender = character.getString("gender")
                val status = character.getString("status")
                val image = character.getString("image")
                val origin = character.getJSONObject("origin").getString("name")
                val location = character.getJSONObject("location").getString("name")
                val characterObj = Character(id, name, species, gender, status, image, origin, location)
                characterList.add(characterObj)
            }

            return characterList
        } catch (e: Exception) {
            Log.e(TAG, "Error reading JSON file: ${e.message}")
            return null
        }
    }
}