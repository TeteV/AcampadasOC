package com.example.frontend.controller.io

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.frontend.controller.models.Persona
import com.example.frontend.controller.models.Reserva
import com.example.frontend.controller.models.Zone
import org.json.JSONArray
import org.json.JSONObject

class ServiceImpl: IVolleyService {

    override fun getAll(context: Context, completionHandler: (response: ArrayList<Zone>?) -> Unit) {
        val path = ServiceSingleton.getInstance(context).baseUrl + "zona"
        val arrayRequest = JsonArrayRequest(Request.Method.GET, path, null,
            { response ->
                var zones: ArrayList<Zone> = ArrayList()
                for (i in 0 until response.length()) {
                    val zone = response.getJSONObject(i)
                    val id = zone.getInt("id")
                    val nombre = zone.getString("nombre")
                    val localizacion = zone.getString("localizacion")
                    val url_img = zone.getString("url_img")

                    zones.add(Zone(id, nombre, localizacion, url_img))
                }
                completionHandler(zones)
            },
            { error ->
                completionHandler(ArrayList<Zone>())
            })
        ServiceSingleton.getInstance(context).addToRequestQueue(arrayRequest)
    }

    override fun getZoneById(context: Context, zoneId: Int, completionHandler: (response: Zone?) -> Unit) {
        val path = ServiceSingleton.getInstance(context).baseUrl + "zona/" + zoneId
        val objectRequest = JsonObjectRequest(Request.Method.GET, path, null,
                { response ->
                    if(response == null) completionHandler(null)
                    val id = response.getInt("id")
                    val nombre = response.getString("nombre")
                    val localizacion = response.getString("localizacion")
                    val url_img = response.getString("url_img")

                    val zone = Zone(id, nombre, localizacion, url_img)
                    completionHandler(zone)
                },
                { error ->
                    completionHandler(null)
                })
        ServiceSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun getPersonById(context: Context, personaId: Int, completionHandler: (response: Persona?) -> Unit) {
        val path = ServiceSingleton.getInstance(context).baseUrl + "persona/" + personaId
        val objectRequest = JsonObjectRequest(Request.Method.GET, path, null,
            { response ->
                if(response == null) completionHandler(null)
                val id = response.getInt("id")
                val nombre = response.getString("nombre")
                val apellidos = response.getString("apellidos")
                val dni = response.getString("dni")
                val url_img = response.getString("url_img")

                val persona = Persona(id, nombre, apellidos, dni, url_img)
                completionHandler(persona)
            },
            { error ->
                completionHandler(null)
            })
        ServiceSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun getBookingById(context: Context, zoneId: Int, completionHandler: (response: Reserva?) -> Unit) {
        val path = ServiceSingleton.getInstance(context).baseUrl + "reserva/" + zoneId
        val objectRequest = JsonObjectRequest(Request.Method.GET, path, null,
            { response ->
                if(response == null) completionHandler(null)
                val id = response.getInt("id")
                val id_persona = response.getInt("id_persona")
                val fecha_entrada = response.getString("fecha_entrada")
                val fecha_salida = response.getString("fecha_salida")
                val localizador_reserva = response.getString("localizador_reserva")
                val num_personas = response.getInt("num_personas")
                val num_vehiculos = response.getInt("num_vehiculos")
                val checkin = response.getString("checkin")
                val fecha_checkin = response.getString("fecha_checkin")
                val id_zona = response.getInt("id_zona")

                val reserva = Reserva(id, id_persona, fecha_entrada, fecha_salida, localizador_reserva, num_personas, num_vehiculos, checkin, fecha_checkin, id_zona)
                completionHandler(reserva)
            },
            { error ->
                completionHandler(null)
            })
        ServiceSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun getBooking(context: Context, zoneId: Int, completionHandler: (response: ArrayList<Reserva>?) -> Unit) {
        val path = ServiceSingleton.getInstance(context).baseUrl + "reserva_zone/" + zoneId
        val arrayRequest = JsonArrayRequest(Request.Method.GET, path, null,
            { response ->
                var bookings: ArrayList<Reserva> = ArrayList()
                for (i in 0 until response.length()) {
                    val booking = response.getJSONObject(i)
                    val id = booking.getInt("id")
                    val id_persona = booking.getInt("id_persona")
                    val fecha_entrada = booking.getString("fecha_entrada")
                    val fecha_salida = booking.getString("fecha_salida")
                    val localizador_reserva = booking.getString("localizador_reserva")
                    val num_personas = booking.getInt("num_personas")
                    val num_vehiculos = booking.getInt("num_vehiculos")
                    val checkin = booking.getString("checkin")
                    val fecha_checkin = booking.getString("fecha_checkin")
                    val id_zona = booking.getInt("id_zona")
                    bookings.add(Reserva(id, id_persona, fecha_entrada, fecha_salida, localizador_reserva, num_personas, num_vehiculos, checkin, fecha_checkin, id_zona))
                }
                completionHandler(bookings)
            },
            { error ->
                completionHandler(ArrayList<Reserva>())
            })
        ServiceSingleton.getInstance(context).addToRequestQueue(arrayRequest)
    }

    override fun getBookingByDate(context: Context, zoneId: Int, date:String, completionHandler: (response: ArrayList<Reserva>?) -> Unit) {
        val path = ServiceSingleton.getInstance(context).baseUrl + "reserva_zone/" + zoneId + "/" + date
        val arrayRequest = JsonArrayRequest(Request.Method.GET, path, null,
            { response ->
                var bookings: ArrayList<Reserva> = ArrayList()
                for (i in 0 until response.length()) {
                    val booking = response.getJSONObject(i)
                    val id = booking.getInt("id")
                    val id_persona = booking.getInt("id_persona")
                    val fecha_entrada = booking.getString("fecha_entrada")
                    val fecha_salida = booking.getString("fecha_salida")
                    val localizador_reserva = booking.getString("localizador_reserva")
                    val num_personas = booking.getInt("num_personas")
                    val num_vehiculos = booking.getInt("num_vehiculos")
                    val checkin = booking.getString("checkin")
                    val fecha_checkin = booking.getString("fecha_checkin")
                    val id_zona = booking.getInt("id_zona")
                    bookings.add(Reserva(id, id_persona, fecha_entrada, fecha_salida, localizador_reserva, num_personas, num_vehiculos, checkin, fecha_checkin, id_zona))
                }
                completionHandler(bookings)
            },
            { error ->
                completionHandler(ArrayList<Reserva>())
            })
        ServiceSingleton.getInstance(context).addToRequestQueue(arrayRequest)
    }

    override fun updateReserve(context: Context, reserva: Reserva, completionHandler: () -> Unit) {
        val path = ServiceSingleton.getInstance(context).baseUrl + "reservaUpdate/" + reserva.id
        val bookingJSON: JSONObject = JSONObject()
        bookingJSON.put("id", reserva.id.toString())
        bookingJSON.put("id_persona", reserva.id_persona.toString())
        bookingJSON.put("fecha_entrada", reserva.fecha_entrada)
        bookingJSON.put("fecha_salida", reserva.fecha_salida)
        bookingJSON.put("localizador_reserva", reserva.localizador_reserva)
        bookingJSON.put("num_personas", reserva.num_personas.toString())
        bookingJSON.put("num_vehiculos", reserva.num_vehiculos.toString())
        bookingJSON.put("checkin", reserva.checkin)
        bookingJSON.put("fecha_checkin", reserva.fecha_checkin)
        bookingJSON.put("id_zona", reserva.id_zona.toString())

        val objectRequest = JsonObjectRequest(Request.Method.PUT, path, bookingJSON,
                { response -> completionHandler() },
                { error -> completionHandler() })
        ServiceSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }


}