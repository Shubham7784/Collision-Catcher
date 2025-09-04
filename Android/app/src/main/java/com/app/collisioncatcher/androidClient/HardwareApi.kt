package com.app.collisioncatcher.androidClient

import com.app.collisioncatcher.Entity.Hardware
import com.app.collisioncatcher.Entity.MapData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface HardwareApi {

    @POST("/hardware")
    fun addHardware(@Body hardware: Hardware,): Call<Hardware>

    @PUT("/hardware")
    fun updateHardware(@Body hardware: Hardware):Call<Hardware>

    @GET("/hardware/disable-motor")
    fun disableMotor():Call<String>

    @GET("/hardware/enable-motor")
    fun enableMotor():Call<String>

    @GET("/hardware/get-gps-data")
    fun getGpsData():Call<MapData>
}