package com.example.guessthenumber.service

import com.example.guessthenumber.RandomIntApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.random.org/json-rpc/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val randomIntApi: RandomIntApi by lazy {
        retrofit.create(RandomIntApi::class.java)
    }

}