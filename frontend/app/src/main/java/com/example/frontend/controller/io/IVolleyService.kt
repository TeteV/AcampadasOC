package com.example.frontend.controller.io

import android.content.Context
import com.example.frontend.controller.models.Zone

interface IVolleyService {

    fun getAll(context: Context, completionHandler: (response: ArrayList<Zone>?) -> Unit)

}