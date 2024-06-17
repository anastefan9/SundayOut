package com.example.sundayout.data

import com.example.sundayout.model.Business
import com.example.sundayout.network.ApiService

interface BusinessRepository {
    suspend fun getBusinesses(): List<Business>
}

class NetworkBusinessRepository(private val apiService: ApiService): BusinessRepository {
    override suspend fun getBusinesses(): List<Business> {
        return apiService.getBusinesses()
    }
}