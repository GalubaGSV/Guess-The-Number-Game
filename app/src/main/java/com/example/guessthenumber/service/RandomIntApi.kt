package com.example.guessthenumber.service

import com.example.guessthenumber.model.RandomInt
import com.example.guessthenumber.model.RandomOrgRequest
import com.example.guessthenumber.model.RandomOrgResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RandomIntApi {
    @POST("4/invoke")
    suspend fun getRandomNumber(@Body request: RandomOrgRequest): RandomOrgResponse
}