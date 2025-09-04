package com.app.collisioncatcher.Acitivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.app.collisioncatcher.Entity.Hardware
import com.app.collisioncatcher.Entity.User
import com.app.collisioncatcher.R
import com.app.collisioncatcher.androidClient.HardwareApi
import com.app.collisioncatcher.androidClient.SignUpApi
import com.app.soulplace.androidClient.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : ComponentActivity() {

    private lateinit var nameText : EditText
    private lateinit var ageText : EditText
    private lateinit var emailText : EditText
    private lateinit var phoneText : EditText
    private lateinit var hardwareSerialText : EditText
    private lateinit var passwordText : EditText
    private lateinit var registerButton : Button
    private lateinit var alreadyAUser : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_design)
        initializeComponents()
    }

    private fun initializeComponents()
    {
        nameText = findViewById<EditText>(R.id.fullNameEditText)
        ageText = findViewById<EditText>(R.id.ageEditText)
        emailText = findViewById<EditText>(R.id.signupemailEditText)
        phoneText = findViewById<EditText>(R.id.phoneEditText)
        hardwareSerialText = findViewById<EditText>(R.id.hardwareSerialEditText)
        passwordText = findViewById<EditText>(R.id.signuppasswordEditText)
        registerButton = findViewById<Button>(R.id.registerButton)
        alreadyAUser = findViewById<TextView>(R.id.alreadyUserTextView)

        registerButton.setOnClickListener{
            signup()
        }
        alreadyAUser.setOnClickListener{
            showLogin()
        }
    }
    private fun signup()
    {
        val userSignup = User()
        userSignup.name = nameText.text.toString()
        userSignup.userName = emailText.text.toString()
        userSignup.age = ageText.text.toString()
        userSignup.password = passwordText.text.toString()
        val hardware = Hardware()
        hardware.serialNo = hardwareSerialText.text.toString()
        userSignup.phoneNo = phoneText.text.toString()

        RetrofitService().getPlaneRetrofit().create<SignUpApi>(SignUpApi::class.java).signup(userSignup).enqueue(object: Callback<User>
        {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if(response.code()==201)
                {
                    saveHardware(hardware,response.body())
                }
                else
                {
                    Log.e("Error While Signup !",response.message())
                    Toast.makeText(this@SignupActivity,"Error While Signup ! Please recheck the fields",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                Toast.makeText(this@SignupActivity,"Error While Signup ! Please Try Again",Toast.LENGTH_LONG).show()
                Log.e("Error While Signup !",t.message.toString())
            }
        })
    }

    private fun saveHardware(hardware: Hardware, userSignup: User?)
    {
        hardware.userName = userSignup?.userName
        val client = RetrofitService().getPlaneRetrofit()
        client.create<HardwareApi>(HardwareApi::class.java).addHardware(hardware).enqueue(object:
            Callback<Hardware>
        {
            override fun onResponse(call: Call<Hardware?>, response: Response<Hardware?>) {
                if(response.code()==201)
                {
                    Toast.makeText(this@SignupActivity,"Signup SuccessFull ! Please Login",Toast.LENGTH_LONG).show()
                    showLogin()
                }
                else
                {
                    Log.e("Error While Saving Hardware !",response.message())
                    Toast.makeText(this@SignupActivity,"Error While Signup ! Please recheck the fields",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Hardware?>, t: Throwable) {
                Toast.makeText(this@SignupActivity,"Error While Connecting to the Server",Toast.LENGTH_SHORT).show()
                Log.e("Error While Connecting to Server",t.message.toString())
            }
        })
    }

    private fun showLogin()
    {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}