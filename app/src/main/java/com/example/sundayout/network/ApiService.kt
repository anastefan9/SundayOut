package com.example.sundayout.network

import com.example.sundayout.model.Business
import com.example.sundayout.model.SignupRequest
import com.example.sundayout.model.SignupResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @GET("fetch_businesses_data")
    suspend fun getBusinesses(): List<Business>
}

