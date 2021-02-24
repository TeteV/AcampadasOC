package com.example.frontend.controller.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.frontend.R
import com.example.frontend.controller.io.ServiceImpl
import com.example.frontend.controller.models.Operario
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getDataLogin()

        /*buttonSingIn.setOnClickListener {
            goToMainActivity()
        }*/
    }

    private fun goToMainActivity(){
        val intent= Intent(this, ZoneActivity::class.java)
        startActivity(intent)
    }

    private fun logIn(context: Context, operario: Operario){
        val serviceImpl = ServiceImpl()
        serviceImpl.logIn(this,operario)
        {
            run {
                Log.v("LoginActc","Login creado")

                /*intent.putExtra("api_token", operario.api_token)
                intent.putExtra("iduser", operario.id)
                context.startActivity(intent)*/
            }
        }
    }

    private fun getDataLogin(){
        Log.v("getData", "cqrgado")
        //Aqui añadir el apitoken
        val et_email = findViewById(R.id.input) as EditText
        val et_password = findViewById(R.id.imput2) as EditText
        val btn_submit = findViewById(R.id.buttonSingIn) as Button


        // set on-click listener
        btn_submit.setOnClickListener {
            Log.v("getData", "picao")
            val email = et_email.text;
            val password = et_password.text;

            val operario = Operario(0,email.toString(),password.toString(),"","","")
            logIn(this,operario)
        }
    }
}