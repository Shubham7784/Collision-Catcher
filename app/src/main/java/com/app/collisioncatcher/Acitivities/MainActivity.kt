package com.app.collisioncatcher.Acitivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.app.collisioncatcher.R
import com.app.soulplace.androidClient.RetrofitService
import com.app.soulplace.androidClient.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_design)
        checkLogin()
    }

    private fun checkLogin() {
        val bToken = getToken()
        val client = RetrofitService().getAuthRetrofit(bToken).create<UserApi>(UserApi::class.java)
        client.isLoggedIn().enqueue(object: Callback<String>
        {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if(response.code()==200)
                {
                    showHome(response.body().toString())
                }
                else
                {
                    Toast.makeText(this@MainActivity,"Token Expired !! Please Login",Toast.LENGTH_SHORT).show()
                    showLogin()
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Server Not Responding , Please restrat the app !!",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showHome(username: String)
    {
        val intent  = Intent(this, HomeActivity::class.java)
        intent.putExtra("UserName",username)
        startActivity(intent)
        finish()
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
    }
    private fun clearToken()
    {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
    }
}