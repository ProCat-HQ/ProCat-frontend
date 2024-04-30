package com.example.procatfirst.repository.api

import com.example.procatfirst.data.Categories
import com.example.procatfirst.data.CategoryRoute
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface CategoriesService {

    @POST("/categories/{categoryId}")
    suspend fun createCategory(@Query("categoryId") categoryId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/categories/route/{categoryId}")
    suspend fun getCategoryRoute(@Query("categoryId") categoryId: Int): Call<CategoryRoute>

    @GET("/categories/{categoryId}")
    suspend fun getAllCategories(@Query("categoryId") categoryId: Int):Call<Categories>

    @PATCH("/categories/{categoryId}")
    suspend fun editName(@Query("categoryId") categoryId: Int, @Body requestBody: RequestBody):Call<ResponseBody>

    @DELETE("/categories/{categoryId}")
    suspend fun deleteRoute(@Query("categoryId") categoryId: Int):Call<ResponseBody>
}