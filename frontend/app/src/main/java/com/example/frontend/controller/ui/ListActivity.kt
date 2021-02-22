package com.example.frontend.controller.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Reserva
import com.example.frontend.controller.util.ReservaAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_reserva_detallada.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListActivity : AppCompatActivity() {
    private lateinit var state: String

    private lateinit var reserva: ArrayList<Reserva>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ReservaAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    @RequiresApi(Build.VERSION_CODES.N)
    private val selectedCalendar: Calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.N)
    private val selectedCalendar2: Calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        reserva = ArrayList<Reserva>()

        viewManager = LinearLayoutManager(this)
        viewAdapter = ReservaAdapter(reserva, this)
        recyclerView = findViewById<RecyclerView>(R.id.locationViewPager2)
        // use a linear layout manager
        recyclerView.layoutManager = viewManager
        // specify an viewAdapter (see also next example)
        recyclerView.adapter = viewAdapter

        state = this.intent.getStringExtra("state").toString()
        val zoneId = this.intent.getIntExtra("zoneId", 1)
        if(state == "Showing") getZone(zoneId)
        val timeZone = "GMT+1"
        val prueba = "1";

        getBookingsDate(zoneId, obtenerFechaActual(timeZone).toString())

        imageButtonCreate.setOnClickListener {
            val intent = Intent(this, CreateReservaActivity::class.java)
            intent.putExtra("zoneId", zoneId)
            startActivity(intent)
        }
    }

    private fun getBookingsDate(zoneId: Int, prueba: String) {
        val roomServiceImpl = ServiceImpl()
        roomServiceImpl.getBookingByDate(this, zoneId, prueba) { response ->
            run {
                if (response != null) {
                    viewAdapter.notifyDataSetChanged()
                    viewAdapter.reservaList = response
                }
                viewAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getZone(zoneId: Int) {
        val bicycleServiceImpl = ServiceImpl()
        bicycleServiceImpl.getZoneById(this, zoneId) { response ->
            run {
                val url = "http://192.168.56.1:8000/img/"
                val imageUrl = url + response?.url_img + ".jpg"
                Picasso.with(this).load(imageUrl).into(bg_lists);
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickSheduleDate(v: View?){

        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener{ datePicker, y, m, d ->
            //Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
            selectedCalendar.set(y, m, d)
            reservationDate.setText(resources.getString(R.string.date_format, y, (m + 1).twoDigits(), d.twoDigits()))
            getBookingsDate(1, resources.getString(R.string.date_format, y, (m + 1).twoDigits(), d.twoDigits()))
        }

        // new dialog
        val datePickerDialog = DatePickerDialog(this, listener, year, month, dayOfMonth)
        val datePicker = datePickerDialog.datePicker

        // show dialog
        datePickerDialog.show()
    }

    private fun Int.twoDigits()
            = if (this>=10) this.toString() else "0$this"
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