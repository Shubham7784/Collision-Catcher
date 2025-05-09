package com.app.collisioncatcher.androidClient

import com.app.collisioncatcher.Entity.Hardware
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface HardwareApi {

    @POST("/hardware")
    fun addHardware(@Body hardware: Hardware,): Call<Hardware>

    @PUT("/hardware")
    fun updateHardware(@Body hardware: Hardware):Call<Hardware>
}