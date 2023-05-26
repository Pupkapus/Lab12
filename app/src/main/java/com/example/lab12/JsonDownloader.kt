package com.example.lab12

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class JsonDownloader(private val context: Context, private val callback: JsonDownloadCallback) {

    private val TAG = "JsonDownloader"

    private val jsonBuilder = StringBuilder()

    fun downloadJsonFile(url: String) {
        JsonDownloadTask().execute(url)
    }

    private inner class JsonDownloadTask : AsyncTask<String, Void, Boolean>() {

        override fun doInBackground(vararg urls: String): Boolean {
            val url = URL(urls[0])
            var connection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            var fileOutputStream: FileOutputStream? = null

            try {
                val file = context.getFileStreamPath("character.json")
                if (file.exists()) {
                    file.delete()
                }

                var nextPageUrl = url.toString()

                while (nextPageUrl.isNotEmpty()) {
                    connection = URL(nextPageUrl).openConnection() as HttpURLConnection
                    connection.connect()

                    if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                        Log.e(TAG, "Server returned HTTP ${connection.responseCode}")
                        return false
                    }

                    fileOutputStream = FileOutputStream(file, true)
                    inputStream = BufferedInputStream(connection.inputStream)

                    val buffer = ByteArray(1024)
                    var bytesRead: Int

                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead)
                        jsonBuilder.append(String(buffer, 0, bytesRead, Charsets.UTF_8))
                    }

                    val jsonData = jsonBuilder.toString()
                    val jsonObject = JSONObject(jsonData)

                    val resultsArray = jsonObject.getJSONArray("results")

                    val existingData = JSONObject(file.readText())
                    val existingResultsArray = existingData.getJSONArray("results")

                    for (i in 0 until resultsArray.length()) {
                        val result = resultsArray.getJSONObject(i)
                        existingResultsArray.put(result)
                    }

                    fileOutputStream.close()
                    fileOutputStream = FileOutputStream(file, false)
                    fileOutputStream.write(existingData.toString().toByteArray())

                    nextPageUrl = getNextPageUrl(jsonData)
                    jsonBuilder.clear()

                    connection.disconnect()
                    inputStream.close()
                    fileOutputStream.close()
                }

                return true
            } catch (e: Exception) {
                Log.e(TAG, "Error downloading JSON file: ${e.message}")
                return false
            } finally {
                connection?.disconnect()
                inputStream?.close()
                fileOutputStream?.close()
            }
        }

        override fun onPostExecute(result: Boolean) {
            if (result) {
                Log.d(TAG, "JSON file downloaded successfully")
                callback.onJsonDownloadComplete(result)
            } else {
                Log.d(TAG, "Failed to download JSON file")
                callback.onJsonDownloadComplete(result)
            }
        }

        private fun getNextPageUrl(jsonData: String): String {
            val json = JSONObject(jsonData)
            val info = json.optJSONObject("info")
            return info?.optString("next", "") ?: ""
        }
    }
}