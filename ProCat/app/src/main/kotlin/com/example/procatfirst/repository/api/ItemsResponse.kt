package com.example.procatfirst.repository.api

//data class ItemsResponse(val items: List<Item>)

data class ItemsResponse(

    val status: String,
    val message: String,
    val payload : List<Item>,

)