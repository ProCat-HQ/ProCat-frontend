package com.example.procatfirst.data


data class ClusterPayload(
    val status: Int,
    val message: String,
    val result: List<ClusterResult>
)

data class ClusterResult(
    val deliverymanId: Int,
    val deliveries: List<DeliveryLocation>
)

data class DeliveryLocation(
    val address: String,
    val latitude: String,
    val longitude: String,
    val deliveryId: Int
)

data class ChangeDeliveryRequest(
    val deliverymanId: Int,
    val deliveryId: Int
)

data class AllDeliveriesToSortResponse(
    val status: Int,
    val message: String,
    val payload: AllDeliveriesToSortPayload
)

data class AllDeliveriesToSortPayload(
    val count: Int,
    val rows: List<ClusterResult>
)

