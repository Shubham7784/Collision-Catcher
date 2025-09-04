package com.app.collisioncatcher.androidClient

import com.app.collisioncatcher.Entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {
    @POST("/public/signup")
    fun signup(@Body user :User) : Call<User>

}