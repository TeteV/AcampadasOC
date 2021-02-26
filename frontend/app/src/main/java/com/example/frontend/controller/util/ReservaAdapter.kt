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
import com.example.frontend.controller.ui.ReservaDetalladaActivity
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
            val iconChecked: ImageView = itemView.findViewById(R.id.iconChecked)

            entrada.text = b.fecha_entrada
            salida.text = b.fecha_salida

            if(b.checkin == "1"){
                iconChecked.setBackgroundResource(R.drawable.icon_checked)
            }else{
                iconChecked.setBackgroundResource(R.drawable.icon_nonchecked)
            }

            roomServiceImpl.getPersonById(context, b.id_persona) { response ->
                run {
                    if (response != null) {
                        val url = "http://192.168.56.1:8000/img/"
                        val name: TextView = itemView.findViewById(R.id.textEntrada3)
                        val apellidos: TextView = itemView.findViewById(R.id.textEntrada4)
                        val dni: TextView = itemView.findViewById(R.id.textDni)
                        val imagePerson: ImageView = itemView.findViewById(R.id.imagePerson)

                        name.setText(response?.name ?: "")
                        apellidos.setText(response?.apellidos ?: "")
                        dni.setText(response?.dni ?: "")

                        val imageUrl = url + response.url_img + ".png"
                        Picasso.with(context).load(imageUrl).into(imagePerson);
                    }
                    itemView.setOnClickListener {
                        val intent = Intent(context, ReservaDetalladaActivity::class.java)
                        intent.putExtra("reservaId", b.id)
                        intent.putExtra("localizador", b.localizador_reserva)
                        intent.putExtra("checkin", b.checkin)
                        intent.putExtra("zoneId", b.id_zona)
                        intent.putExtra("personId", response?.id)
                        intent.putExtra("personName", response?.name)
                        intent.putExtra("state", "Showing")
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

}