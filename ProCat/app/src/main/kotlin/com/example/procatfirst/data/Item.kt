package com.example.procatfirst.data

import kotlinx.serialization.Serializable


@Serializable
data class Item(
        //place for Vadim
        val id: Int,
        val name: String,
        val description: String,
        val price: Int,
        val isInStock: Boolean,
        val image: String,
        val categoryId: Int?,


)

@Serializable
data class Item2(
        val id: Int,
        val name: String,
        val description: String,
        val price: Int,
        val isInStock: Boolean,
        val categoryId: Int?,
        val categoryName: String,
        val image: String
)
