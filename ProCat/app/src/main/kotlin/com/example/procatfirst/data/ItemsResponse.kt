package com.example.procatfirst.data

import kotlinx.serialization.Serializable


@Serializable
data class ItemsResponse(

    val status: String,
    val message: String,
    val payload : List<Item>
)

data class ItemResponse(
        val status: Int,
        val message: String,
        val payload: ItemPayload
)

data class ItemPayload(
        val count: Int,
        val rows: List<Item>
)

//------------------

data class ItemInfo(
        val id: Int,
        val name: String,
        val description: String
)

data class ItemImage(
        val id: Int,
        val image: String
)

data class ItemStore(
        val id: Int,
        val inStockNumber: Int,
        val name: String,
        val address: String,
        val workingHoursStart: String,
        val workingHoursEnd: String
)

data class ItemFullPayload(
        val id: Int,
        val name: String,
        val description: String,
        val price: Int,
        val isInStock: Boolean,
        val categoryId: Int,
        val categoryName: String,
        val info: List<ItemInfo>,
        val images: List<ItemImage>,
        val itemStores: List<ItemStore>
)


