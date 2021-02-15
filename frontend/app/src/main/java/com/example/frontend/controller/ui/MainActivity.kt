package com.example.frontend.controller.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Zone
import com.example.frontend.controller.util.ZoneAdapter
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var zones: ArrayList<Zone>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ZoneAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    //private lateinit var binding: ActivityMainBinding

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        //setContentView(binding.root)
        //binding.qrElement.setOnClickListener { initScanner() }

        zones = ArrayList<Zone>()

        viewManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        viewAdapter = ZoneAdapter(zones, this)
        recyclerView = findViewById<RecyclerView>(R.id.locationViewPager)
        // use a linear layout manager
        recyclerView.layoutManager = viewManager
        // specify an viewAdapter (see also next example)
        recyclerView.adapter = viewAdapter
        getAllRooms()
        listeners()

    }
/*
    private fun initScanner() {
        Log.v("qr", "dentro del init")
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }*/

    private fun listeners() {
        val helpBtn = findViewById<Button>(R.id.helpBtn)
        helpBtn.setOnClickListener {
            val intent = Intent(this, WebView::class.java)
            startActivity(intent)
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "cancelado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "el valor escaneado es: ${result.contents}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }*/

    private fun getAllRooms() {
        val roomServiceImpl = ServiceImpl()
        roomServiceImpl.getAll(this) { response ->
            run {
                if (response != null) {
                    viewAdapter.zoneLists = response
                }
                viewAdapter.notifyDataSetChanged()
            }
        }
    }
}