package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Operario
import com.example.frontend.controller.models.Zone

class addZoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_zone)
        listeners()
    }

    private fun listeners() {
        val addBtn = findViewById<Button>(R.id.addZoneBtn)
        addBtn.setOnClickListener {
            addZone()
        }
    }

    private fun addZone() {

        val localizacion: String = findViewById<EditText>(R.id.editTextLocation).text.toString()
        val nombre: String = findViewById<EditText>(R.id.editTextName).text.toString()

        Log.v("Addzone","Loca: "+localizacion)
        Log.v("Addzone","nomrbe: "+nombre)

        val zone = Zone(0,nombre,localizacion ,"")
        Log.v("Create", zone.toString())
        createZone(zone)
    }

    private fun createZone(zone: Zone) {
        val ServiceImpl = ServiceImpl()
        ServiceImpl.createZone(this, zone) { ->
            run {
                Log.v("createZone","ZonaCreada")
                /*val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)*/
            }
        }
    }


}