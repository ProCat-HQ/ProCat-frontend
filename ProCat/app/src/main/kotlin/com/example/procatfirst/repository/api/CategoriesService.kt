package com.example.procatfirst.repository.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface CategoriesService {

    @POST("/categories/categoryId")
    fun createCategory(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/categories/route/categoryId")
    fun getCategoryRoute(): Call<ResponseBody>

    @GET("/categories/categoryId")
    fun getAllCategories():Call<ResponseBody>

    @PATCH("/categories/categoryId")
    fun editName(@Body requestBody: RequestBody):Call<ResponseBody>

    @DELETE("/categories/categoryId")
    fun deleteRoute(@Body requestBody: RequestBody):Call<ResponseBody>
}