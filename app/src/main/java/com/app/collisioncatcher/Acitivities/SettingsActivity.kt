package com.app.collisioncatcher.Acitivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.app.collisioncatcher.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var darkModeSwitch: Switch
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_design)
        darkModeSwitch = findViewById(R.id.darkModeSwitch)
        logoutButton = findViewById(R.id.logoutButton)

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        logoutButton.setOnClickListener {
            showLogin()
        }
    }

    private fun showLogin()
    {
        clearToken()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun clearToken()
    {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
    }

}
