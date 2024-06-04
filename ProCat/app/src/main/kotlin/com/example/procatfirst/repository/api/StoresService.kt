package com.example.procatfirst.repository.api

import com.example.procatfirst.data.NewStoreResponse
import com.example.procatfirst.data.StoreResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StoresService {

    @GET("/stores")
    fun getAllStores(): Call<StoreResponse>

    @POST("/stores")
    fun createNewStore(@Header("Authorization") token: String?, @Body requestBody: RequestBody): Call<NewStoreResponse>

    @DELETE("/stores/{storeId}")
    suspend fun deleteStore(@Query("storeId") storeId: Int): Call<ResponseBody>

    @PATCH("/stores/{storeId}")
    fun updateStore(@Header("Authorization") token: String?, @Path("storeId") storeId: Int, @Body requestBody: RequestBody): Call<ResponseBody>
}