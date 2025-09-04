package com.app.collisioncatcher.Acitivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.app.collisioncatcher.Entity.User
import com.app.collisioncatcher.R
import com.app.soulplace.androidClient.RetrofitService
import com.app.soulplace.androidClient.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : ComponentActivity() {

    private lateinit var emailText : EditText
    private lateinit var passwordText : EditText
    private lateinit var loginButton : Button
    private lateinit var signupButton : TextView
    private lateinit var forgotPassword : TextView
    private lateinit var userName : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_design)
        initializeComponents()
    }
    private fun initializeComponents()
    {
        emailText = findViewById<EditText>(R.id.emailEditText)
        passwordText = findViewById<EditText>(R.id.passwordEditText)
        loginButton = findViewById<Button>(R.id.loginButton)
        signupButton = findViewById<TextView>(R.id.registerTextView)
        forgotPassword = findViewById<TextView>(R.id.forgotPasswordTextView)

        loginButton.setOnClickListener{
            login()
        }
        signupButton.setOnClickListener{
            signup()
        }
        forgotPassword.setOnClickListener{
            forgotPassword()
        }
    }
    private fun login()
    {
        if(emailText.text.isEmpty()||passwordText.text.isEmpty())
        {
            Toast.makeText(this,"Fields Are Mandatory !",Toast.LENGTH_SHORT).show()
            return
        }
        val userLogin = User()
        userLogin.userName = emailText.text.toString()
        userLogin.password = passwordText.text.toString()
        userName = userLogin.userName
        val client = RetrofitService().getPlaneRetrofit().create<UserApi>(UserApi::class.java)
        client.login(userLogin).enqueue(object: Callback<String>
        {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if(response.code()==200)
                {
                    showHome(response.body().toString())
                }
                else
                {
                    Log.e("Error While LogIn",response.message())
                    Toast.makeText(this@LoginActivity,"Invalid Credentials ! Try Again",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.e("Error While LogIn",t.message.toString())
                Toast.makeText(this@LoginActivity,"Some Error Occurred While Connecting To The Server !",Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun signup()
    {
        val intent = Intent(this,SignupActivity::class.java)
        startActivity(intent)
    }

    private fun forgotPassword()
    {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }
    private fun showHome(bToken:String)
    {
        saveToken(bToken)
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("UserName",userName)
        startActivity(intent)

    }
    private fun saveToken(bToken: String) {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("BToken",bToken)
        editor.apply()
    }
    private fun getToken(): String
    {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreference.getString("BToken",null).toString()
        return sharedPreference.getString("BToken",null).toString()
    }
    private fun clearToken()
    {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
    }
}
