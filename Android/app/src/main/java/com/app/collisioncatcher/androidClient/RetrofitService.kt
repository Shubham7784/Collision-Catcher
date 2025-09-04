package com.app.soulplace.androidClient

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://e14b3602e0e7.ngrok-free.app")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))

    fun getPlaneRetrofit(): Retrofit
    {
        val client = OkHttpClient().newBuilder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
        return retrofit.client(client).build()
    }

    fun getAuthRetrofit(bToken: String): Retrofit
    {
        val client = OkHttpClient().newBuilder()
            .connectTimeout(1,TimeUnit.MINUTES)
            .readTimeout(1,TimeUnit.MINUTES)
            .writeTimeout(1,TimeUnit.MINUTES)
            .addInterceptor(BasicAuthInterceptor(bToken))
            .build()

        return retrofit.client(client).build()
    }
}