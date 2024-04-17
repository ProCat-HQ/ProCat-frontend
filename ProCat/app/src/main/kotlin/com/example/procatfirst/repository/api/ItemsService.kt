package com.example.procatfirst.repository.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

interface ItemsService {

    @POST("/items/categoryId")
    fun createCategory(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/items") //query params
    fun getAllItems(): Call<ResponseBody>

    @GET("/items/itemId")
    fun getItemWithInfo(@Body requestBody: RequestBody): Call<ResponseBody>

    @DELETE("/items/itemId")
    fun deleteItem(): Call<ResponseBody>

    @PATCH("/items/itemId")
    fun editItem(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/items")
    fun createItem(@Body requestBody: RequestBody): Call<ResponseBody>

    @PUT("/items/stock/itemId")
    fun createOrChangeStock(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/items/infos/itemId")
    fun addCharacteristics(@Body requestBody: RequestBody): Call<ResponseBody>

    @DELETE("/items/infos/itemId")
    fun deleteInfos(@Body requestBody: RequestBody): Call<ResponseBody>

    @PATCH("/items/infos/itemId")
    fun changeInfo(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/items/images/itemId")
    fun addImagesToItem(@Body requestBody: RequestBody): Call<ResponseBody>

    @DELETE("/items/images/itemId")
    fun deleteImages(@Body requestBody: RequestBody): Call<ResponseBody>

}