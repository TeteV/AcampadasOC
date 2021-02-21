package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Operario

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        listeners()
    }

    private fun listeners(){
        val signupBtn = findViewById<Button>(R.id.buttonSingIn)
        signupBtn.setOnClickListener {
            val id = this.intent.getIntExtra("id", 0)
            val dni: String = findViewById<TextView>(R.id.inputDni).text.toString()
            val name: String = findViewById<TextView>(R.id.inputName).text.toString()
            val email: String = findViewById<TextView>(R.id.input).text.toString()
            val pwd: String = findViewById<TextView>(R.id.imput2).text.toString()

            val operario = Operario(id,email,pwd,name,dni,"")
            Log.v("Create", operario.toString())
            createUser(operario)
        }
    }

    private fun createUser(operario: Operario){
        val ServiceImpl = ServiceImpl()
        ServiceImpl.createUser(this, operario) { ->
            run {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}