package com.example.procatfirst.data

import kotlinx.serialization.Serializable

@Serializable
data class DeliveryDistribution(
    val id: Int,
)

data class Delivery(
    val latitude: Double,
    val longitude: Double,
    val deliveryId: Int
)

data class DeliveryMan(
    val deliveryManId: Int,
    val deliveries: List<Delivery>
)

data class ApiResponseDelivery(
    val message: String,
    val payload: List<DeliveryMan>
)