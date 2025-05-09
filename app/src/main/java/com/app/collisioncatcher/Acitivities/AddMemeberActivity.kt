package com.app.collisioncatcher.Acitivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.collisioncatcher.Entity.Member
import com.app.collisioncatcher.R
import com.app.soulplace.androidClient.RetrofitService
import com.app.soulplace.androidClient.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMemberActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var relationshipInput: EditText
    private lateinit var addMemberButton: Button
    private lateinit var userName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_member_design)

        nameInput = findViewById(R.id.nameInput)
        phoneInput = findViewById(R.id.phoneInput)
        relationshipInput = findViewById(R.id.relationshipInput)
        addMemberButton = findViewById(R.id.addMemberButton)
        userName = intent.getStringExtra("UserName").toString()
        addMemberButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val relation = relationshipInput.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Name and phone are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else
            {
                addMember(name,phone,relation)
            }
        }
    }

    private fun addMember(name:String,phone:String,relation:String)
    {
        val member = Member()
        member.name = name
        member.phoneNo = phone
        member.relation = relation
        val client = RetrofitService().getAuthRetrofit(getToken()).create<UserApi>(UserApi::class.java)
        client.addMember(member).enqueue(object: Callback<Member>
        {
            override fun onResponse(call: Call<Member>, response: Response<Member>) {
                if(response.code()==201)
                {
                    Toast.makeText(this@AddMemberActivity,"Member Added Successfully!",Toast.LENGTH_SHORT).show()
                    showHome()
                }
                else
                {
                    Toast.makeText(this@AddMemberActivity,"Some Error Occurred While Adding Member !",Toast.LENGTH_LONG).show()
                    Log.e("Error While Adding Member",response.message())
                }
            }

            override fun onFailure(call: Call<Member>, t: Throwable) {
                Toast.makeText(this@AddMemberActivity,"Some Error Occurred While Adding Member !! ",Toast.LENGTH_LONG).show()
                Log.e("Error While Connecting To Server During Adding Member",t.message.toString())
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

    private fun showHome()
    {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("UserName",userName)
        startActivity(intent)
        finish()
    }
}
