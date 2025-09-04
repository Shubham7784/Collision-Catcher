package com.app.collisioncatcher.Acitivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.app.collisioncatcher.Entity.Hardware
import com.app.collisioncatcher.Entity.User
import com.app.collisioncatcher.R
import com.app.collisioncatcher.androidClient.HardwareApi
import com.app.soulplace.androidClient.RetrofitService
import com.app.soulplace.androidClient.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : ComponentActivity() {
    private lateinit var nameText: EditText
    private lateinit var ageText: EditText
    private lateinit var emailText: EditText
    private lateinit var phoneText: EditText
    private lateinit var hardwareSerialText: EditText
    private lateinit var saveChangesButton: Button
    private lateinit var userName : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile_design)
        initializeComponents()
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
                    nameText.setText(response.body()?.name.toString())
                    emailText.setText(response.body()?.userName.toString())
                    ageText.setText(response.body()?.age.toString())
                    phoneText.setText(response.body()?.phoneNo.toString())
                    hardwareSerialText.setText(response.body()?.hardware?.serialNo.toString())
                }
                else{
                    Toast.makeText(this@EditProfileActivity,"Details Cannot be Fetched!",Toast.LENGTH_SHORT).show()
                    Log.e("Error While Fetching Profile Details",response.message())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@EditProfileActivity,"Some Error Occurred While Connecting to the Server !",Toast.LENGTH_LONG).show()
                Log.e("Error While Fetching Profile Details",t.message.toString())
            }
        })
    }
    private fun initializeComponents()
    {
        nameText = findViewById<EditText>(R.id.editfullNameEditText)
        ageText = findViewById<EditText>(R.id.editageEditText)
        emailText = findViewById<EditText>(R.id.editemailEditText)
        phoneText = findViewById<EditText>(R.id.editphoneEditText)
        hardwareSerialText = findViewById<EditText>(R.id.edithardwareSerialEditText)
        saveChangesButton = findViewById<Button>(R.id.saveChangesButton)

        userName = intent.getStringExtra("UserName").toString()
        saveChangesButton.setOnClickListener{
            saveChanges()
        }
    }

    private fun saveChanges()
    {
        val editedUser = User()
        editedUser.name = nameText.text.toString()
        editedUser.age = ageText.text.toString()
        editedUser.userName = emailText.text.toString()
        editedUser.phoneNo = phoneText.text.toString()
        editedUser.hardware = Hardware()
        editedUser.hardware.serialNo = hardwareSerialText.text.toString()
        editedUser.hardware.userName = userName

        RetrofitService().getAuthRetrofit(getToken()).create<UserApi>(UserApi::class.java).updateUserDetails(editedUser).enqueue(object:
            Callback<User>
        {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if(response.code()==202)
                {
                    saveHardware(editedUser.hardware)
                }
                else
                {
                    Log.e("Some Error Occurred While Saving Changes !",response.message())
                    Toast.makeText(this@EditProfileActivity,"Some Error Occurred ! Try Again",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                Toast.makeText(this@EditProfileActivity,"Some Error Occured While Connecting to the Server", Toast.LENGTH_SHORT).show()
                Log.e("Some Error Occured While Connecting to the Server",t.message.toString())
            }
        })
    }

    private fun getToken(): String
    {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreference.getString("BToken",null).toString()
        return sharedPreference.getString("BToken",null).toString()
    }

    private fun saveHardware(hardware: Hardware)
    {
        RetrofitService().getPlaneRetrofit().create<HardwareApi>(HardwareApi::class.java).updateHardware(hardware).enqueue(object:
            Callback<Hardware>
        {
            override fun onResponse(call: Call<Hardware?>, response: Response<Hardware?>) {
                if(response.code()==202)
                {
                    showProfile()
                    Toast.makeText(this@EditProfileActivity,"Profile Updated Successfully !",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Log.e("Some Error Occurred While Updating Hardware",response.message())
                }
            }

            override fun onFailure(call: Call<Hardware?>, t: Throwable) {
                Toast.makeText(this@EditProfileActivity,"Error While Connecting to the Server",Toast.LENGTH_SHORT).show()
                Log.e("Error While Connecting to the Server",t.message.toString())

            }
        })
    }
    private fun showProfile()
    {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("UserName",intent.getStringExtra("UserName"))
        startActivity(intent)
        finish()
    }
}