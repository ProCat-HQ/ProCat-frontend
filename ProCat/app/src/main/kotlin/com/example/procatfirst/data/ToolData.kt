package com.example.procatfirst.data

import kotlinx.serialization.Serializable

@Serializable
data class Tool(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val isInStock: Boolean,
    val categoryId: Int,
    val image: String,
)