package com.app.collisioncatcher.Acitivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.app.collisioncatcher.R

class ForgotPasswordActivity : ComponentActivity() {
    private lateinit var emailText : EditText
    private lateinit var sendEmail : Button
    private lateinit var backLogin : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password_design)
        initializeComponents()
    }

    private fun initializeComponents() {
        emailText = findViewById<EditText>(R.id.forgotemailEditText)
        sendEmail = findViewById<Button>(R.id.resetPasswordButton)
        backLogin = findViewById<TextView>(R.id.backToLoginTextView)

        sendEmail.setOnClickListener{
            sendEmail()
        }

        backLogin.setOnClickListener{
            showLogin()
        }
    }

    private fun showLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun sendEmail() {
        TODO("Not yet implemented")
    }
}
