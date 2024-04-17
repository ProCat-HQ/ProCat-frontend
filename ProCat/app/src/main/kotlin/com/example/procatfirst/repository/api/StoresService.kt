package com.example.procatfirst.repository.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface StoresService {

    @GET("/stores")
    fun getAllStores(): Call<ResponseBody>

    @POST("/stores")
    fun createNewStore(@Body requestBody: RequestBody)

    @DELETE("/stores/storeId")
    fun deleteStore(): Call<ResponseBody>

    @PATCH("/stores/storeId")
    fun updateStore(@Body requestBody: RequestBody)
}