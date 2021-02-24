package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.net.Uri
import java.io.IOException
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Operario
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val id =4
        getById(id)
        listeners()

    }



    private fun listeners() {
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            //logOutUser()
        }

        /*val imgBtn = findViewById<Button>(R.id.imageBtn)
        imgBtn.setOnClickListener {
            Log.v("ImgBtn","tocao")
            //cargarImagen()
        }*/

        val updateBtn = findViewById<Button>(R.id.updateBtn)
        updateBtn.setOnClickListener {
            updateInfo()
        }

        val deleteBtn = findViewById<Button>(R.id.deleteBtn)
        deleteBtn.setOnClickListener {
            toastDeleteOp()
        }


    }



    /*private fun cargarImagen() {
        val intent= Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/")
        startActivityForResult(intent.createChooser(intent,"sele"),010)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imagen = findViewById<ImageView>(R.id.imageViewPhoto)
        if (resultCode== RESULT_OK){
            val path=data.
            imagen.setImageURI(path)
        }
    }*/

    private fun toastDeleteOp() {
        val id =3
        val nombre: String = findViewById<TextView>(R.id.editTextName).text.toString()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete")
        builder.setMessage("Are you sure to delete you account " + nombre + " ?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(
                    applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT
            ).show()
            deleteUser(id)
            Log.v("Edit", "borrao")
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                    applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()
    }

    private fun updateInfo() {
        val id =4
        val dni: String = findViewById<TextView>(R.id.editTextDni).text.toString()
        val nombre: String = findViewById<TextView>(R.id.editTextName).text.toString()
        val email: String = findViewById<TextView>(R.id.editTextEmail).text.toString()
        val operario = Operario(id,email,"",nombre,dni,"")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update")
        // builder.setMessage("Update User with: \n Dni: "+ dni + " \n Name: " + name \n Dni: "+ dni + ")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(
                    applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT
            ).show()
            try {
                updateUser(operario)
            }
            catch (e: Exception) {
                Log.v("Edit", "Error en el catch")
            }
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                    applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()
    }

    private fun updateUser(operario: Operario) {
        val ServiceImpl = ServiceImpl()
        ServiceImpl.updateUser(this, operario) { ->
            run {
                Log.v("Link", "Iria al List")
                /*val intent = Intent(this, PostDetailActivity::class.java)
                startActivity(intent)*/
            }
        }
    }

    private fun getById(id: Int) {
        Log.v("Getbyid", "Estoy aqui: "+id)
        val ServiceImpl = ServiceImpl()
        ServiceImpl.getOpById(this, id) { response ->
            run {
                val nombre: TextView = findViewById(R.id.editTextName)
                val nombreTV: TextView = findViewById(R.id.textViewName)
                val email: TextView = findViewById(R.id.editTextEmail)
                val dni: TextView = findViewById(R.id.editTextDni)
                //val url = "http://192.168.203.73:8000/users_image/"//clase
                //val url = "http://192.168.1.129:8000/users_image/"//casa

                nombreTV.setText(response?.nombre ?: "")
                nombre.setText(response?.nombre ?: "")
                email.setText(response?.email ?: "")
                dni.setText(response?.dni ?: "")
                /*val imageUrl = url + r.url_img
                Picasso.with(context).load(imageUrl).into(img);*/
            }
        }
    }

    private fun deleteUser(id:Int) {
        val ServiceImpl = ServiceImpl()
        ServiceImpl.deleteUser(this, id) { ->
            run {
                Toast.makeText(this, "Usuario borrado", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
