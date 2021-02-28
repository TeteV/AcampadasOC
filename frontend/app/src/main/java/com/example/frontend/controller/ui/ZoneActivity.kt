package com.example.frontend.controller.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Zone
import com.example.frontend.controller.util.ZoneAdapter
import com.example.frontend.databinding.ActivityZoneBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.example.frontend.controller.util.PreferenceHelper
import com.example.frontend.controller.util.PreferenceHelper.set

class ZoneActivity : AppCompatActivity() {

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    private lateinit var zones: ArrayList<Zone>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ZoneAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityZoneBinding

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZoneBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_zone)
        setContentView(binding.root)
        binding.qrElement.setOnClickListener { initScanner() }

        zones = ArrayList<Zone>()

        viewManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        viewAdapter = ZoneAdapter(zones, this)
        recyclerView = findViewById<RecyclerView>(R.id.locationViewPager2)
        // use a linear layout manager
        recyclerView.layoutManager = viewManager
        // specify an viewAdapter (see also next example)
        recyclerView.adapter = viewAdapter

        val num : Int = this.intent.getIntExtra("num",0)

        val tokenGE : String = this.intent.getStringExtra("api_token").toString()
        val opeIdGE : String = this.intent.getStringExtra("opeId").toString()
        Log.v("ZoneActiGE",opeIdGE)
        Log.v("ZoneActiEG",tokenGE)
        Log.v("ZoneActiNumEG", num.toString())

        if (num==1){
            Log.v("Create Pref","Create Pref")
            createSessionPreference(tokenGE, opeIdGE.toInt())
        }

        val opeIdPref = preferences.getInt("opeId", 0)
        val tokenPref = preferences.getString("token", null)
        Log.v("ZoneActi ID pref", opeIdPref.toString())
        Log.v("ZoneActi token pref",tokenPref.toString())

        getAllRooms()
        listeners()
    }

    private fun initScanner() {
        Log.v("qr", "dentro del init")
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    private fun listeners() {
        val helpBtn = findViewById<Button>(R.id.helpBtn2)
        helpBtn.setOnClickListener {
            val intent = Intent(this, WebView::class.java)
            startActivity(intent)
        }

        val profibleBtn = findViewById<ImageView>(R.id.avatarProfile2)
        profibleBtn.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        val addZoneBtn = findViewById<Button>(R.id.addZoneBtn)
        addZoneBtn.setOnClickListener {
            val intent = Intent(this, addZoneActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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

    private fun createSessionPreference(tokenPref: String, opeId: Int) {
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["tokenPref"] = tokenPref
        preferences["opeId"] = opeId
    }

}




