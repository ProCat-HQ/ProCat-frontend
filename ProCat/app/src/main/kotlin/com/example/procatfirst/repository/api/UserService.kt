package com.example.procatfirst.repository.api

import com.example.procatfirst.BuildConfig
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/* Retrofit service that maps the different endpoints on the API, you'd create one
 * method per endpoint, and use the @Path, @Query and other annotations to customize
 * these at runtime */

interface UserService {


    @POST("/login")
    fun setUsers(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("geocode?q=Новосибирск, Пирогова 1&fields=items.point&key=${BuildConfig.apiKey}")
    fun geocoder(): Call<ResponseBody>

    @GET("/items")
    fun getItems(): Call<ItemsResponse>

}