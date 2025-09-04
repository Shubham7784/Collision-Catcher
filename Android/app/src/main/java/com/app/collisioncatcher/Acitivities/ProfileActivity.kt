package com.app.collisioncatcher.Acitivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.collisioncatcher.Entity.User
import com.app.collisioncatcher.R
import com.app.soulplace.androidClient.RetrofitService
import com.app.soulplace.androidClient.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var usernameText: TextView
    private lateinit var emailText: TextView
    private lateinit var ageText : TextView
    private lateinit var phoneTextView: TextView
    private lateinit var hardwareSerialText : TextView
    private lateinit var editProfileButton: Button
    private lateinit var userName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_design)

        usernameText = findViewById(R.id.usernameText)
        emailText = findViewById(R.id.emailText)
        editProfileButton = findViewById(R.id.editProfileButton)
        ageText = findViewById<TextView>(R.id.profileAge)
        phoneTextView = findViewById<TextView>(R.id.profilePhoneNo)
        hardwareSerialText = findViewById<TextView>(R.id.profileHardwareSerialNo)
        editProfileButton.setOnClickListener {
            showEditProfile()
        }
        userName = intent.getStringExtra("UserName").toString()
        loadUserDetials()
    }
    private fun loadUserDetials()
    {
        val client = RetrofitService().getAuthRetrofit(getToken()).create<UserApi>(UserApi::class.java)
        client.getUserDetails().enqueue(object: Callback<User>
        {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.code()==200)
                {
                    usernameText.text = response.body()?.name
                    emailText.text = response.body()?.userName
                    ageText.text = "${ageText.text} ${response.body()?.age}"
                    phoneTextView.text = "${phoneTextView.text} ${response.body()?.phoneNo}"
                    hardwareSerialText.text = "${hardwareSerialText.text} ${response.body()?.hardware?.serialNo}"
                }
                else{
                    Toast.makeText(this@ProfileActivity,"Details Cannot be Fetched!",Toast.LENGTH_SHORT).show()
                    Log.e("Error While Fetching Profile Details",response.message())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@ProfileActivity,"Some Error Occurred While Connecting to the Server !",Toast.LENGTH_LONG).show()
                Log.e("Error While Fetching Profile Details",t.message.toString())
            }
        })

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

    private fun showEditProfile()
    {
        val intent = Intent(this,EditProfileActivity::class.java)
        intent.putExtra("UserName",userName)
        startActivity(intent)
    }
}

