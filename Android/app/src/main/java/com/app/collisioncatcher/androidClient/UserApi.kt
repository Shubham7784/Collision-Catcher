package com.app.soulplace.androidClient

import com.app.collisioncatcher.Entity.Member
import com.app.collisioncatcher.Entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    @POST("/public/login")
    fun login(@Body user: User): Call<String>

    @GET("/user/check-login")
    fun isLoggedIn():Call<String>

    @POST("/member")
    fun addMember(@Body member: Member): Call<Member>

    @GET("/member/getAll")
    fun getMembers():Call<ArrayList<Member>>

    @GET("/user/getUserDetails")
    fun getUserDetails():Call<User>

    @PUT("/user/update")
    fun updateUserDetails(@Body updatedUser : User):Call<User>

}