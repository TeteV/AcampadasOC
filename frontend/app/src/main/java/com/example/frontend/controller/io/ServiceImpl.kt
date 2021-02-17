package com.example.frontend.controller.io

import android.R
import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.frontend.controller.models.Operario
import com.example.frontend.controller.models.Zone
import com.example.frontend.controller.ui.MainActivity
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
                        val intent = Intent(context, MainActivity::class.java)
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
    
}