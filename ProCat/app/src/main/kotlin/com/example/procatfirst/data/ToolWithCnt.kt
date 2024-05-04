package com.example.procatfirst.data

import kotlinx.serialization.Serializable

@Serializable
data class ToolWithCnt(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val isInStock: Boolean,
    val categoryId: Int,
    val imageResId: Int,
    val cnt: Int,
)