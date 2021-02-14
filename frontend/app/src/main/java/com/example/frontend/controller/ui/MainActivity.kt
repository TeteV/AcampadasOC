package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.frontend.R
import com.example.frontend.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)
        binding.qrElement.setOnClickListener{initScanner()}
        listeners()

    }

    private fun initScanner() {
        Log.v("qr","dentro del init")
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    private fun listeners() {
        val helpBtn = findViewById<Button>(R.id.helpBtn)
        helpBtn.setOnClickListener {
            val intent = Intent(this, WebView::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(result!=null){
            if(result.contents == null){
                Toast.makeText(this,"cancelado",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"el valor escaneado es: ${result.contents}",Toast.LENGTH_SHORT).show()
            }
        }else{
          super.onActivityResult(requestCode, resultCode, data)
        }

    }
}