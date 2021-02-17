package com.example.frontend.controller.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Reserva
import com.example.frontend.controller.models.Zone
import com.example.frontend.controller.ui.ListActivity
import com.squareup.picasso.Picasso

class ReservaAdapter (var reservaList: ArrayList<Reserva>, val context: Context): RecyclerView.Adapter<ReservaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_container_reservas, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(reservaList[position], context)
    }

    override fun getItemCount(): Int {
        return reservaList.size;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(b: Reserva, context: Context){
            val roomServiceImpl = ServiceImpl()

            val entrada: TextView = itemView.findViewById(R.id.textEntrada)
            val salida: TextView = itemView.findViewById(R.id.textEntrada2)

            roomServiceImpl.getPersonById(context, b.id_persona) { response ->
                run {
                    if (response != null) {
                        val name: TextView = itemView.findViewById(R.id.textEntrada3)
                        name.setText(response?.name ?: "")
                    }
                }
            }

            entrada.text = b.fecha_entrada
            salida.text = b.fecha_salida

            itemView.setOnClickListener {
                val intent = Intent(context, ListActivity::class.java)
                intent.putExtra("zoneId", b.id)
                intent.putExtra("state", "Showing")
                context.startActivity(intent)
            }
        }
    }
}