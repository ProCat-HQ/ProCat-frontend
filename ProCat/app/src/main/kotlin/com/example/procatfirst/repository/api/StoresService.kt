package com.example.procatfirst.repository.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface StoresService {

    @GET("/stores")
    suspend fun getAllStores(): Call<ResponseBody>

    @POST("/stores")
    suspend fun createNewStore(@Body requestBody: RequestBody)

    @DELETE("/stores/{storeId}")
    suspend fun deleteStore(@Query("storeId") storeId: Int): Call<ResponseBody>

    @PATCH("/stores/{storeId}")
    suspend fun updateStore(@Query("storeId") storeId: Int, @Body requestBody: RequestBody)
}