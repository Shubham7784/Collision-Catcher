package com.app.collisioncatcher.Acitivities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.collisioncatcher.Contact.ContactAdapter
import com.app.collisioncatcher.Entity.Member
import com.app.collisioncatcher.R
import com.app.soulplace.androidClient.RetrofitService
import com.app.soulplace.androidClient.UserApi
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var addMemberButton: Button
    private lateinit var navHeaderEmail : TextView
    private lateinit var userName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_design)

        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.contactRecyclerView)
        addMemberButton = findViewById(R.id.addMemberButton)
        val headerView = navView.getHeaderView(0)
        navHeaderEmail = headerView.findViewById<TextView>(R.id.navHeaderEmail)
        userName = intent.getStringExtra("UserName").toString()

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        getMembers()
        addMemberButton.setOnClickListener {
            val intent = Intent(this, AddMemberActivity::class.java)
            intent.putExtra("UserName", userName)
            startActivity(intent)
            finish()
        }
        setupNavigationDrawer()
    }

    private fun getMembers(){
        val client = RetrofitService().getAuthRetrofit(getToken()).create<UserApi>(UserApi::class.java)
        client.getMembers().enqueue(object : Callback<ArrayList<Member>>
        {
            override fun onResponse(call: Call<ArrayList<Member>>,response: Response<ArrayList<Member>>)
            {
                if(response.code()==200) {
                    setupRecyclerView(response.body()!!)
                }
                else
                {
                    val contacts = ArrayList<Member>()
                    setupRecyclerView(contacts)
                    Log.e("Error While Retrieving Members",response.message())
                }
            }

            override fun onFailure(call: Call<ArrayList<Member>>, t: Throwable) {
                Toast.makeText(this@HomeActivity,"Some Error Occurred While Contacting to the Server",Toast.LENGTH_LONG).show()
                Log.e("Some Error Occurred While Getting All Members",t.message.toString())
            }
        })

    }

    private fun setupRecyclerView(contacts:ArrayList<Member>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ContactAdapter(contacts)
    }

    private fun setupNavigationDrawer() {
        navHeaderEmail.text = intent.getStringExtra("UserName")
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> showHome()
                R.id.nav_track_speed -> showTrackSpeed()
                R.id.nav_theft_protection -> showTheftProtection()
                R.id.nav_profile -> showProfile()
                R.id.nav_settings -> showSettings()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
    private fun showTrackSpeed()
    {
        val intent = Intent(this, SpeedTrackingActivity::class.java)
        startActivity(intent)
    }

    private fun showTheftProtection()
    {
        val intent = Intent(this, TheftProtectionActivity::class.java)
        startActivity(intent)
    }

    private fun showHome()
    {}
    private fun showProfile()
    {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("UserName",userName)
        startActivity(intent)
    }
    private fun showSettings()
    {
        val intent = Intent(this, SettingsActivity::class.java)
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

