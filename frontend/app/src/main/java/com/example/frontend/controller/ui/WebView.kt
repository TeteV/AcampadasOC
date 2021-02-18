package com.example.frontend.controller.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.frontend.R

class WebView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val myWebView: WebView = findViewById(R.id.webview)
        //JAvi myWebView.loadUrl("http://192.168.56.1:8000/help")
        myWebView.loadUrl("http://192.168.1.129:8000/help")
    }
}