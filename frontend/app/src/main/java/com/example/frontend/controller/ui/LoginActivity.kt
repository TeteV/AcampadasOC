package com.example.frontend.controller.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.frontend.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSingIn.setOnClickListener {
            goToMainActivity()
        }
    }
    private fun goToMainActivity(){
        val intent= Intent(this, ZoneActivity::class.java)
        startActivity(intent)
    }
}