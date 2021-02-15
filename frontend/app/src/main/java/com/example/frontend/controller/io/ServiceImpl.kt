package com.example.frontend.controller.io

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.frontend.controller.models.Zone

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


}