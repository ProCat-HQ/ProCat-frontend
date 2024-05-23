package com.example.procatfirst.data


data class StoreResponse(
    val status: Int,
    val message: String,
    val payload: List<Store>
)

data class Store(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val workingHoursStart: String,
    val workingHoursEnd: String
)

data class StoreRequest(
    val name: String,
    val address: String,
    val workingHoursStart: String,
    val workingHoursEnd: String
)

data class NewStoreResponse(
    val status: Int,
    val message: String,
    val payload: IdPayload
)

data class IdPayload(
    val id: Int
)