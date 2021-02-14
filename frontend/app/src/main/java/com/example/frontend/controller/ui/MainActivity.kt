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

class MainActivity : AppCompatActivity() {

    private lateinit var zones: ArrayList<Zone>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ZoneAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    private fun listeners() {
        val helpBtn = findViewById<Button>(R.id.helpBtn)
        helpBtn.setOnClickListener {
            Log.v("help","picao")
            val intent = Intent(this, WebView::class.java)
            startActivity(intent)
        }
    }

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