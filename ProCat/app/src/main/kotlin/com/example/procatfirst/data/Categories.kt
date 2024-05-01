package com.example.procatfirst.data

data class Category(
    val id: Int,
    val name: String,
    val parentId: Int
)

data class CategoryRoute(
    val route: List<Category>
)

data class Categories(
    val catiegories: List<Category>
)