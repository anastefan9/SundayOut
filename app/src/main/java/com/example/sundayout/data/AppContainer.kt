package com.example.sundayout.data

import com.example.sundayout.network.ApiService
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val signupRepository: SignupRepository
    val businessRepository: BusinessRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl = "http://10.0.2.2:8000/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val signupRepository: SignupRepository by lazy {
        DataSignUpRepository(retrofitService)
    }

    override val businessRepository: BusinessRepository by lazy {
        NetworkBusinessRepository(retrofitService)
    }
}