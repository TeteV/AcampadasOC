package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.frontend.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        button.setOnClickListener {
            goToLoginActivity()
        }

        textViewSignIn.setOnClickListener {
            goToRegisterActivity()
        }
    }

    private fun goToLoginActivity(){
        val intent= Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun goToRegisterActivity() {
        val intent= Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}