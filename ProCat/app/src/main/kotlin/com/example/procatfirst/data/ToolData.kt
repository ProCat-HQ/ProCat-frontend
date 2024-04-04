package com.example.procatfirst.data

import com.example.procatfirst.R
import kotlinx.serialization.Serializable

@Serializable
data class Tool(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val isInStock: Boolean,
    val categoryId: Int,
    val imageResId: Int
)



object ToolDataProvider {
    val tools = listOf(
        Tool(
            id = 1,
            name = "Молоток",
            imageResId = R.drawable.hammer,
            description = "Очень качественный, громкий",
            price = 200,
            isInStock = true,
            categoryId = 1
        ),
        Tool(
            id = 2,
            name = "Набор",
            imageResId = R.drawable.set,
            description = "Все что надо - в одном месте",
            isInStock = true,
            price = 400,
            categoryId = 2
        ),
        Tool(
            id = 2,
            name = "Набор",
            imageResId = R.drawable.set,
            description = "Все что надо - в одном месте",
            isInStock = false,
            categoryId = 3,
            price = 400
        ),
    )
}