package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Zone
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_update_zone.*
import kotlinx.android.synthetic.main.activity_update_zone.bg_lists

class UpdateZoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_zone)

        val zoneId = this.intent.getIntExtra("zoneId", 0)

        getZone(zoneId)

        updateBtn2.setOnClickListener {
            toastUpdateZone(zoneId)
        }
    }

    private fun toastUpdateZone(zoneId: Int) {
        val zone = Zone(zoneId, editTextNombreZona2.text.toString(), editTextZona2.text.toString() ,"")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update")
        builder.setMessage("Are you sure to update " + editTextNombreZona2.text.toString())
        builder.setMessage("With " + editTextNombreZona2.text.toString() + " "+ editTextZona2.text.toString() + " ?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(
                    applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT
            ).show()
            updateZone(zone)
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                    applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()
    }

    private fun updateZone(zone: Zone) {
        val ServiceImpl = ServiceImpl()
        ServiceImpl.updateZone(this, zone) { ->
            run {
                Log.v("updatezone", "update")
                Toast.makeText(this, "Zona update", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ZoneActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getZone(zoneId: Int) {
        val bicycleServiceImpl = ServiceImpl()
        bicycleServiceImpl.getZoneById(this, zoneId) { response ->
            run {
                //val url = "http://192.168.56.1:8000/img/"
                val url = "https://cryptic-dawn-95434.herokuapp.com/img/"
                val imageUrl = url + response?.url_img + ".jpg"
                Picasso.with(this).load(imageUrl).into(bg_lists);
                editTextZona2.setText(response?.nombre.toString() ?: "")
                editTextNombreZona2.setText(response?.localizacion.toString() ?: "")
            }
        }
    }
}