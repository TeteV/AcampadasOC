package com.example.frontend.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.frontend.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listeners()
    }

    private fun listeners() {
        val helpBtn = findViewById<Button>(R.id.helpBtn)
        helpBtn.setOnClickListener {
            Log.v("help","picao")
            val intent = Intent(this, WebView::class.java)
            startActivity(intent)
        }
    }
}