package com.example.frontend.controller.io

import android.content.Context
import com.example.frontend.controller.models.Operario
import com.example.frontend.controller.models.Persona
import com.example.frontend.controller.models.Reserva

import com.example.frontend.controller.models.Zone

interface IVolleyService {

    fun getAll(context: Context, completionHandler: (response: ArrayList<Zone>?) -> Unit)


    fun getZoneById(context: Context, zoneId: Int, completionHandler: (response: Zone?) -> Unit)

    fun getBookingById(context: Context, zoneId: Int, completionHandler: (response: Reserva?) -> Unit)

    fun getPersonById(context: Context, zoneId: Int, completionHandler: (response: Persona?) -> Unit)

    fun getBooking(context: Context, userId: Int, completionHandler: (response: ArrayList<Reserva>?) -> Unit)

    fun getBookingByDate(context: Context, userId: Int, date:String, completionHandler: (response: ArrayList<Reserva>?) -> Unit)

    fun updateReserve(context: Context,  reserva: Reserva, completionHandler: () -> Unit)

    fun getOpById(context: Context, id: Int, completionHandler: (response: Operario?) -> Unit)
    fun updateUser(context: Context, operario: Operario, completionHandler: () -> Unit)
    fun deleteUser(context: Context, id:Int, completionHandler: () -> Unit)
    fun logIn(context: Context, operario: Operario, completionHandler: () -> Unit)
    fun createUser(context: Context, operario: Operario, completionHandler: () -> Unit)

}