package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Operario
import com.example.frontend.controller.util.PreferenceHelper
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val id = preferences.getInt("opeId", 0)
        getById(id)
        listeners()

    }

    private fun listeners() {
        button_reportes.setOnClickListener {
            val intent = Intent(this, PDFView::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        val updateBtn = findViewById<Button>(R.id.updateBtn)
        updateBtn.setOnClickListener {
            updateInfo()
        }

        val deleteBtn = findViewById<Button>(R.id.deleteBtn)
        deleteBtn.setOnClickListener {
            toastDeleteOp()
        }


    }

    private fun toastDeleteOp() {
        val nombre: String = findViewById<TextView>(R.id.editTextName).text.toString()
        val builder = AlertDialog.Builder(this)
        val id = preferences.getInt("opeId", 0)
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
        val id = preferences.getInt("opeId", 0)
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
        Log.v("Getbyid", "Estoy aqui: " + id)
        val ServiceImpl = ServiceImpl()
        ServiceImpl.getOpById(this, id) { response ->
            run {
                val nombre: TextView = findViewById(R.id.editTextName)
                val nombreTV: TextView = findViewById(R.id.textViewName)
                val email: TextView = findViewById(R.id.editTextEmail)
                val dni: TextView = findViewById(R.id.editTextDni)

                nombreTV.setText(response?.nombre ?: "a")
                nombre.setText(response?.nombre ?: "a")
                email.setText(response?.email ?: "a")
                dni.setText(response?.dni ?: "a")
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
