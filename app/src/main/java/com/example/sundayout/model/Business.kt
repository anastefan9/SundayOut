package com.example.sundayout.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Business(
    val businessId: Int,
    @SerialName(value = "adminId_id")
    val adminId: Int,
    val name: String,
    val latitude: String,
    val longitude: String,
    val score: String,
    val priceRange: String,
    val image: String
)