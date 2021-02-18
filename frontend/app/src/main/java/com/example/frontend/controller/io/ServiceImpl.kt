package com.example.frontend.controller.io

import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.frontend.controller.models.Operario

import com.example.frontend.controller.models.Zone
import com.example.frontend.controller.ui.ZoneActivity
import org.json.JSONObject
import com.example.frontend.controller.models.Persona
import com.example.frontend.controller.models.Reserva

import org.json.JSONArray


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

    override fun logIn(context: Context, operario: Operario, completionHandler: () -> Unit){
        Log.v("LoginService", "Auqi")
        val path = ServiceSingleton.getInstance(context).baseUrl + "login"
        val operarioJson = JSONObject()
        Log.v("LoginService", "Auqi1")
        operarioJson.put("email", operario.email)
        operarioJson.put("password", operario.password)
        operarioJson.put("api_token", operario.api_token)
        Log.v("LoginService", "Auqi2")
        val objectRequest = JsonObjectRequest(Request.Method.POST, path, operarioJson,
                { response ->
                    completionHandler()
                    Log.v("LoginService", "Auqi3")
                    val plus = response?.opt("res")
                    val tokn = response?.opt("api_token").toString()
                    val id = response?.opt("id").toString()
                    Log.v("LoginService", "plus: " + plus)
                    Log.v("login", "token: " + tokn)
                    Log.v("login", "Id: " + id)
                    Log.v("login", "response: " + response.toString())
                    if (plus == true) {
                        Log.v("loginService", "Login correcto")
                        val intent = Intent(context, ZoneActivity::class.java)
                        /* intent.putExtra("api_token", tokn)
                        intent.putExtra("id_user",id_us)*/
                        context.startActivity(intent)
                    } else {
                        Log.v("loginService", "Login incorrecto")

                    }
                },
                { error ->
                    completionHandler()
                    Log.v("login", "Error en login service")
                })
       ServiceSingleton.getInstance(context).addToRequestQueue(objectRequest)
    }

    override fun createUser(context: Context, operario: Operario, completionHandler: () -> Unit) {
        val path = ServiceSingleton.getInstance(context).baseUrl + "signin"
        val operarioJson: JSONObject = JSONObject()
        operarioJson.put("id", 0)
        operarioJson.put("dni",operario.dni)
        operarioJson.put("nombre",operario.nombre)
        operarioJson.put("email",operario.email)
        operarioJson.put("password",operario.password)

        Log.v("createenService","Operario: "+operarioJson)

        val objectRequest = JsonObjectRequest(Request.Method.POST, path, operarioJson,
            { response -> completionHandler()
                val plus = response?.opt("res")
                if (plus==true){
                    Log.v("AddUser","Creado")
                }else{
                    Log.v("AddUser", "Check Dni or Email")
                    //Aqui Toast o algo para decir que mire
                }
            },
            { error -> completionHandler()
                Log.v("AddUser","Roto")
            })
        ServiceSingleton.getInstance(context).addToRequestQueue(objectRequest)
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