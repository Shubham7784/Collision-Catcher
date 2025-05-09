package com.app.collisioncatcher.Acitivities

import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.app.collisioncatcher.R

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
                engineStatus.text = "Engine is OFF"
            else
                engineStatus.text = "Engine is ON"
        }
    }
}