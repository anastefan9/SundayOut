package com.example.sundayout.data

import com.example.sundayout.model.SignupRequest
import com.example.sundayout.model.SignupResponse
import com.example.sundayout.network.ApiService
import retrofit2.Response

interface SignupRepository {
    suspend fun signupUser(firstName: String, lastName: String, email: String, password: String): Response<SignupResponse>
}

class DataSignUpRepository(private val apiService: ApiService): SignupRepository {
    override suspend fun signupUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Response<SignupResponse> {
        return apiService.signup(SignupRequest(firstName = firstName, lastName = lastName, email = email, password = password))
    }
}