package com.app.collisioncatcher.Acitivities

import android.os.Bundle
import android.util.Log
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.app.collisioncatcher.Entity.Hardware
import com.app.collisioncatcher.R
import com.app.collisioncatcher.androidClient.HardwareApi
import com.app.soulplace.androidClient.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TheftProtectionActivity : ComponentActivity() {
    private lateinit var engineToggleSwitch: Switch
    private lateinit var engineStatus: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.theft_protection_design)

        initializeComponents()
    }

    private fun initializeComponents() {
        engineToggleSwitch = findViewById<Switch>(R.id.engineToggle)
        engineStatus = findViewById<TextView>(R.id.engineStatusText)

        engineToggleSwitch.setOnCheckedChangeListener{_,isChecked->
            if(isChecked)
            {
                disableMotor()
                engineStatus.text = "Engine is OFF"
            }
            else
            {
                enableMotor()
                engineStatus.text = "Engine is ON"
            }
        }
    }
    private fun disableMotor() {
        RetrofitService().getPlaneRetrofit().create<HardwareApi>(HardwareApi::class.java)
            .disableMotor().enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.code() == 200) {
                    engineStatus.text = response.body()
                } else {
                    Log.e("Error while disabling motor", response.message())
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.e("Error while connecting to the Server !", t.message.toString())
            }
        })
    }
    private fun enableMotor()
    {
        RetrofitService().getPlaneRetrofit().create<HardwareApi>(HardwareApi::class.java).enableMotor().enqueue(object:
            Callback<String>
        {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if(response.code()==200)
                {
                    engineStatus.text = response.body().toString()
                }
                else
                {
                    Log.e("Error while disabling motor",response.message())
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.e("Error while connecting to the Server !",t.message.toString())
            }
        })
    }
}