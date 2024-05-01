package com.example.procatfirst.data

data class SubscriptionItem(
    val id: Int,
    val name: String,
    val price: Int,
    val isInStock: Boolean,
    val image: String
)

data class SubscriptionResponse(
    val count: Int,
    val rows: List<SubscriptionItem>
)