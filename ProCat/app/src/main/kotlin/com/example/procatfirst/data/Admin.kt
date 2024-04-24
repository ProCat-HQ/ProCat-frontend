package com.example.procatfirst.data


data class ClusterPayload(
    val result: List<ClusterResult>
)

data class ClusterResult(
    val deliverymanId: Int,
    val deliveries: List<DeliveryLocation>
)

data class DeliveryLocation(
    val latitude: String,
    val longitude: String,
    val deliveryId: Int
)

