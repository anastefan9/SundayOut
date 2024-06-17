package com.example.sundayout.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Business(
    val businessId: Int,
    @SerialName(value = "ownerId_id")
    val ownerId: Int,
    val name: String,
    val latitude: String,
    val longitude: String,
    val score: String,
    val priceRange: String,
    val image: String
)