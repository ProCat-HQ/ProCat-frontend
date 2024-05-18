package com.example.procatfirst.data

data class ClusterResponse(
    val status: Int,
    val message: String,
    val payload: ClusterPayload
)
data class ClusterPayload(
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

