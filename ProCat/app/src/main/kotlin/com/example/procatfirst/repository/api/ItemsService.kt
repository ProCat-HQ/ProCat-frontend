package com.example.procatfirst.repository.api

import com.example.procatfirst.data.ItemFullPayload
import com.example.procatfirst.data.ItemResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ItemsService {

    @POST("/items/{categoryId}")
    suspend fun createCategory(@Query("categoryId") categoryId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/items") //query params
    suspend fun getAllItems(): Call<ItemResponse>

    @GET("/items/{itemId}")
    suspend fun getItemWithInfo(@Query("itemId") itemId: Int): Call<ItemFullPayload>

    @DELETE("/items/{itemId}")
    suspend fun deleteItem(@Query("itemId") itemId: Int): Call<ResponseBody>

    @PATCH("/items/{itemId}")
    suspend fun editItem(@Query("itemId") itemId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/items")
    suspend fun createItem(@Body requestBody: RequestBody): Call<ResponseBody>

    @PUT("/items/stock/{itemId}")
    suspend fun createOrChangeStock(@Query("itemId") itemId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/items/infos/{itemId}")
    suspend fun addCharacteristics(@Query("itemId") itemId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @DELETE("/items/infos/{itemId}")
    suspend fun deleteInfos(@Query("itemId") itemId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @PATCH("/items/infos/{itemId}")
    suspend fun changeInfo(@Query("itemId") itemId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/items/images/{itemId}")
    suspend fun addImagesToItem(@Query("itemId") itemId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @DELETE("/items/images/{itemId}")
    suspend fun deleteImages(@Query("itemId") itemId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

}