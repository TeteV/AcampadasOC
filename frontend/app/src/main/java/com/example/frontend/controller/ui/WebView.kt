package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import com.example.frontend.R

class WebView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val myWebView: WebView = findViewById(R.id.webview)
        //JAvi myWebView.loadUrl("http://192.168.56.1:8000/help")
        //myWebView.loadUrl("http://192.168.103.46:8000/help")
        myWebView.loadUrl("http://192.168.103.46:8080/AyudaCanaryCamp.html")

        val backBtn = findViewById<Button>(R.id.backBtn)
        backBtn.setOnClickListener {
            val intent = Intent(this, ZoneActivity::class.java)
            startActivity(intent)
        }
    }
}