package com.example.procatfirst.repository.api

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    //place for Vadim
        val id: Int,
        val name: String,
        val description: String,
        val price: Int,
        val isInStock: Boolean,
        val images: Int,
        val categoryId: Int,

        )
