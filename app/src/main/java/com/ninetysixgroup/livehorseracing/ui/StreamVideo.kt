package com.ninetysixgroup.livehorseracing.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.MediaController
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.ninetysixgroup.livehorseracing.R
import com.ninetysixgroup.livehorseracing.api.videoModel
import kotlinx.android.synthetic.main.activity_stream_video.*
import okhttp3.*
import java.io.IOException

class StreamVideo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stream_video)

        val intent = intent
        val link = intent.getStringExtra("link")

//        Toast.makeText(this, "${link}", Toast.LENGTH_SHORT).show()

        webview.webViewClient = WebViewClient()

//        getStreamChannel("1014", )

    }

    override fun onDestroy() {
        super.onDestroy()
        webview.removeAllViews();
        webview.destroy()
        webview.clearCache(true);
        webview.clearHistory()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun getStreamChannel(channel: String?, userIp: String?, context: Context){
        //show the loading screen in main activity

        val baseURL = "https://3webasketball.com/ninety-six-group-api/public/latest/stream/${channel}/${userIp}/geth5link"
        val request = Request.Builder().url(baseURL).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val video = gson.fromJson(body, videoModel::class.java)
                val link = video.H5LINKROW
                runOnUiThread{
                    webview.apply {
                        link?.let { loadUrl(it) }
                        settings.javaScriptEnabled = true
                    }
                }


            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to request")
            }

        })
    }

}