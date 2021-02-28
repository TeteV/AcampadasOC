package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Reserva
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_create_reserva.*
import kotlinx.android.synthetic.main.activity_update_reserva.*
import kotlinx.android.synthetic.main.activity_update_reserva.buttonUpdate

class CreateReservaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reserva)

        val zoneId = this.intent.getIntExtra("zoneId", 1)
        getZone(zoneId)

        buttonUpdate.setOnClickListener {
            val reserva = Reserva(10, 1, fechaEntradaC.text.toString(), fechaSalidaC.text.toString(), "1", numPC.text.toString().toInt(), numVC.text.toString().toInt(), "0", "0", zoneId)
            createReserve(reserva)
        }
    }

    private fun getZone(zoneId: Int) {
        val bicycleServiceImpl = ServiceImpl()
        bicycleServiceImpl.getZoneById(this, zoneId) { response ->
            run {
                val url = "http://192.168.56.1:8000/img/"
                val roomImg: ImageView = findViewById(R.id.bg_update)
                val imageUrl = url + response?.url_img + ".jpg"

                Picasso.with(this).load(imageUrl).into(roomImg);
            }
        }
    }

    fun createReserve(reserva: Reserva){
        val serviceImpl = ServiceImpl()
        serviceImpl.createReserve(this, reserva) { ->
            run {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}