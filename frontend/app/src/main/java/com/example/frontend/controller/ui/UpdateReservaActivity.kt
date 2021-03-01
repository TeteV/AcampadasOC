package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Reserva
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_reserva_detallada.*
import kotlinx.android.synthetic.main.activity_update_reserva.*

class UpdateReservaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_reserva)

        val personId = this.intent.getIntExtra("personId", 1)
        val reservaId = this.intent.getIntExtra("reservaId", 1)
        val localizador = this.intent.getIntExtra("localizador", 1).toString()
        val check = this.intent.getIntExtra("checkin", 1).toString()
        val fecha_checkin = this.intent.getIntExtra("fecha_checkin", 1).toString()
        val zoneId = this.intent.getIntExtra("zoneId", 1)
        getZone(zoneId)

        buttonUpdate.setOnClickListener {
            val reserva = Reserva(reservaId, personId, fechaEntrada.text.toString(), fechaSalida2.text.toString(), localizador, numP.text.toString().toInt(), numV.text.toString().toInt(), check, fecha_checkin, zoneId)
            updateBooking(reserva)
        }
    }

    private fun getZone(zoneId: Int) {
        val bicycleServiceImpl = ServiceImpl()
        bicycleServiceImpl.getZoneById(this, zoneId) { response ->
            run {
                //val url = "http://192.168.56.1:8000/img/"
                val url = "http://192.168.1.129:8000/img/"
                val roomImg: ImageView = findViewById(R.id.bg_update)
                val imageUrl = url + response?.url_img + ".jpg"

                Picasso.with(this).load(imageUrl).into(roomImg);
            }
            }
        }


    private fun updateBooking(reserva: Reserva) {
        val bookingServiceImpl = ServiceImpl()
        bookingServiceImpl.updateReserve(this, reserva) { ->
            run {
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
