package com.example.venadostest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class ImageDownloader : AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg params: String?): Bitmap? {
        try {
            var url = URL(params[0])
            var connection = url.openConnection() as HttpURLConnection
            connection.connect()

            var inputStream = connection.inputStream

            var bitmap = BitmapFactory.decodeStream(inputStream)
            return bitmap

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}