package com.example.procatfirst.repository.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//data class ItemsResponse(val items: List<Item>)
@Serializable
data class ItemsResponse(

    //val status: String,
    //val message: String,
        val payload : List<Item>,


        )