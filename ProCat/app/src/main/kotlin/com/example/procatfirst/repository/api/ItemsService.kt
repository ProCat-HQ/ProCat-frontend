package com.example.procatfirst.repository.api

import com.example.procatfirst.data.ItemFullResponse
import com.example.procatfirst.data.ItemResponse
import com.example.procatfirst.data.RegistrationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.InputStream

interface ItemsService {

    @POST("/items/{categoryId}")
    suspend fun createCategory(@Query("categoryId") categoryId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/items")
    suspend fun getAllItems(): Call<ItemResponse>

    @GET("/items")
    fun getItems(): Call<ItemResponse>

    @GET("/items/{itemId}")
    fun getItemWithInfo(@Path("itemId") itemId: Int): Call<ItemFullResponse>

    @DELETE("/items/{itemId}")
    fun deleteItem(@Path("itemId") itemId: Int): Call<ResponseBody>

    @PATCH("/items/{itemId}")
    fun editItem(@Path("itemId") itemId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @Multipart
    @POST("/items")
    fun createItem(
        @Header("Authorization") token: String?,
        @Part("name") name: String,
        @Part("description") description: String,
        @Part("price") price: Int,
        @Part("priceDeposit") priceDeposit: Int,
        @Part("categoryId") categoryId: Int,
        @Part images: List<MultipartBody.Part>
    ): Call<RegistrationResponse>


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

    @GET("/assets/hammer.jpg")
    fun downloadFile(): InputStream

}