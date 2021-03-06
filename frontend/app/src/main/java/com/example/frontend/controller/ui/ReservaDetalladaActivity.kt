package com.example.frontend.controller.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Reserva
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_reserva_detallada.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class ReservaDetalladaActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva_detallada)

        //val state = this.intent.getStringExtra("state").toString()
        val timeZone= "GMT+1"
        val personId = this.intent.getIntExtra("personId", 1)
        val reservaId = this.intent.getIntExtra("reservaId", 1)
        val localizador = this.intent.getIntExtra("localizador", 1)
        //val checkIn = this.intent.getIntExtra("checkin", 1)
        val zoneId = this.intent.getIntExtra("zoneId", 1)

        getZone(zoneId)

        getBooking(reservaId)

        getPerson(personId)

        buttonCheckIn.setOnClickListener {
            val reserva = Reserva(reservaId, personId, textEntrada.text.toString(), textEntrada2.text.toString(), localizador.toString(), numPersonsR.text.toString().toInt(),
                    numVehiculosR.text.toString().toInt(), "1", obtenerFechaActual(timeZone).toString(), zoneId)
            updateBooking(reserva)
        }

        buttonDeleteReserva.setOnClickListener {
            deleteBooking(reservaId)
        }

        buttonUpdateReserva.setOnClickListener {
            val bicycleServiceImpl = ServiceImpl()
            bicycleServiceImpl.getBookingById(this, reservaId) { response ->
                run {
                    val intent = Intent(this, UpdateReservaActivity::class.java)
                    intent.putExtra("personId", personId)
                    intent.putExtra("reservaId", reservaId)
                    intent.putExtra("localizador", localizador)
                    intent.putExtra("zoneId", zoneId)
                    intent.putExtra("checkin", response?.checkin ?: "")
                    intent.putExtra("fecha_checkin", response?.fecha_checkin ?: "")
                    startActivity(intent)
                }
            }
        }
    }

    private fun getZone(zoneId: Int) {
        val bicycleServiceImpl = ServiceImpl()
        bicycleServiceImpl.getZoneById(this, zoneId) { response ->
            run {
                //val url = "http://192.168.56.1:8000/img/"
                val url = "http://192.168.1.129:8000/img/"
                val localizacion: TextView = findViewById(R.id.localization)
                val name: TextView = findViewById(R.id.name)
                val roomImg: ImageView = findViewById(R.id.bg)
                val imageUrl = url + response?.url_img + ".jpg"

                localizacion.setText(response?.localizacion ?: "")
                name.setText(response?.nombre ?: "")
                Picasso.with(this).load(imageUrl).into(roomImg);
            }
        }
    }

    private fun getBooking(zoneId: Int) {
        val bicycleServiceImpl = ServiceImpl()
        bicycleServiceImpl.getBookingById(this, zoneId) { response ->
            run {
                val fechaEntrada: TextView = findViewById(R.id.textEntrada)
                val fechaSalida: TextView = findViewById(R.id.textEntrada2)
                val numPerson: TextView = findViewById(R.id.numPersonsR)
                val numVehiculos: TextView = findViewById(R.id.numVehiculosR)

                if(response?.checkin == "1"){
                    buttonCheckIn.setBackgroundResource(R.drawable.bg_button_checked)
                    buttonCheckIn.setText("Checked")
                }else{
                    buttonCheckIn.setBackgroundResource(R.drawable.bg_button)
                }

                fechaEntrada.setText(response?.fecha_entrada ?: "")
                fechaSalida.setText(response?.fecha_salida ?: "")
                numPerson.setText(response?.num_personas.toString() ?: "")
                numVehiculos.setText(response?.num_vehiculos.toString() ?: "")
            }
        }
    }

    private fun getPerson(userId: Int){
        val serviceImpl = ServiceImpl()
        serviceImpl.getPersonById(this, userId) { response ->
            run {
                val url = "http://192.168.1.129:8000/img/"
                val imagePerson: ImageView = findViewById(R.id.imagenPerfilReserva)
                val personNameTV: TextView= findViewById(R.id.textViewNameDude)
                val personNamePUT = this.intent.getStringExtra("personName").toString()
                Log.v("GetPerson", personNamePUT)
                personNameTV.setText(personNamePUT)

                val imageUrl = url + response?.url_img  + ".png"
                Picasso.with(this).load(imageUrl).into(imagePerson);
            }
        }
    }

    private fun updateBooking(reserva: Reserva) {
        val bookingServiceImpl = ServiceImpl()
        bookingServiceImpl.updateReserve(this, reserva) { ->
            run {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun deleteBooking(reservaId: Int){
        val bicycleServiceImpl = ServiceImpl()
        bicycleServiceImpl.deleteByReservaId(this, reservaId) { ->
            run {
                val intent = Intent(this, ListActivity::class.java)
                this.startActivity(intent)
                finish()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    fun obtenerFechaConFormato(formato: String?, zonaHoraria: String?): String? {
        val calendar = Calendar.getInstance()
        val date = calendar.time
        val sdf: SimpleDateFormat
        sdf = SimpleDateFormat(formato)
        sdf.setTimeZone(TimeZone.getTimeZone(zonaHoraria))
        return sdf.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun obtenerFechaActual(zonaHoraria: String?): String? {
        val formato = "yyyy-MM-dd"
        return obtenerFechaConFormato(formato, zonaHoraria)
    }




}