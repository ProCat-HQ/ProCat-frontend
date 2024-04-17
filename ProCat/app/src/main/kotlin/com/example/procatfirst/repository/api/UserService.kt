package com.example.procatfirst.repository.api

import com.example.procatfirst.BuildConfig
import com.example.procatfirst.data.User
import com.example.procatfirst.data.DeliveryDistribution
import com.squareup.moshi.Json
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/* Retrofit service that maps the different endpoints on the API, you'd create one
 * method per endpoint, and use the @Path, @Query and other annotations to customize
 * these at runtime */

interface UserService {

    @GET("/users")
    fun getAllUsers(): Call<ResponseBody>

    @GET("/users/userId")
    fun getSimpleUser(): Call<ResponseBody>

    @DELETE("/users/userId")
    fun deleteUser()

    @POST("/users/sign-up")
    fun register(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/sign-in")
    fun login(@Body requestBody: RequestBody): Call<TokenResponse>

    @POST("/users/verification/send")
    fun createNewVerificationCode(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/verification/check")
    fun checkCode(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("users/verification/iin")
    fun postIin(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/iin-bin")
    fun changeIin(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("users/change/fullname")
    fun changeFullName(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/password")
    fun changePassword(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/phone")
    fun changePhone(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/email")
    fun changeEmail(@Body requestBody: RequestBody)

    @POST("users/change/role/{userId}")
    fun changeRole(@Body requestBody: RequestBody)

    @GET("/users/deliverymen")
    fun getAllDeliverymen(): Call<ResponseBody>

    @GET("/users/deliverymen/{deliveryId}")
    fun getDeliveryMan(): Call<ResponseBody>

    @POST("/users/deliverymen/{userId}")
    fun createDeliveryManFromUser(@Body requestBody: RequestBody): Call<ResponseBody>

    @PATCH("/users/deliverymen/{deliverymanId}")
    fun changeDeliverymanData(@Body requestBody: RequestBody): Call<ResponseBody>

    @DELETE("/users/deliverymen/{deliverymanId}")
    fun deleteDeliveryman(): Call<ResponseBody>

    @GET("/users/deliverymen/deliveries/")
    fun getAllDeliveries(): Call<ResponseBody>

    @GET("/users/deliverymen/deliveries/{deliverymanId}")
    fun getDeliveriesForDeliveryman(): Call<ResponseBody>

    @PATCH("/users/deliverymen/deliveries/{deliverymanId}")
    fun changeStatusForDeliveryman(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/deliverymen/deliveries/create-route")
    fun createRouteFromDeliveryman(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/admin/cluster")
    fun makeCluster(): Call<ResponseBody>

    @GET("/users/admin/deliveries-to-sort")
    fun getAllDeliveriesAfterClustering(): Call<ResponseBody>

    @PATCH("/users/admin/change-delivery")
    fun changeDelivery(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/users/cart/")
    fun getItemsInCart(): Call<ResponseBody>

    @POST("users/cart")
    fun postCart(@Body requestBody: RequestBody)

    @DELETE("/users/cart")
    fun deleteItemFromCart(@Body requestBody: RequestBody)

    @GET("/users/orders/")
    fun getOrders(): Call<ResponseBody>

    @GET("/users/orders/{orderId}")
    fun getOrder(): Call<ResponseBody>

    @POST("/users/orders/")
    fun createNewOrder(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/orders/cancel/{orderId}")
    fun setStatusOfOrder(): Call<ResponseBody>

    @PATCH("/users/orders/status/{orderId}")
    fun changeStatusOfOrder(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/users/orders/payment/{orderId}")
    fun getInfoAboutPayments(@Body requestBody: RequestBody)

    @PATCH("/users/orders/payment/{paymentId}")
    fun updatePaymentInfo(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/users/subscriptions/")
    fun getAllSubsForUser(): Call<ResponseBody>

    @POST("/users/subscriptions/")
    fun addItemIdToSubs(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/users/notifications/")
    fun getAllNotifications(): Call<ResponseBody>

    @POST("/users/notifications/{usersId}")
    fun sendNotification(@Body requestBody: RequestBody): Call<ResponseBody>

    @PATCH("/users/notifications/{notificationId}")
    fun setNotificationToViewed(@Body requestBody: RequestBody): Call<ResponseBody>

    @DELETE("/users/notifications/{notificationId}")
    fun deleteNotification(): Call<ResponseBody>

    @GET("geocode?q=Новосибирск, Пирогова 1&fields=items.point&key=${BuildConfig.apiKey}")
    fun geocoder(): Call<ResponseBody>

    @GET("/items")
    fun getItems(@Header("Authorization") token: String?): Call<ResponseBody>

    @GET("/users/{id}")
    fun getUser(@Query("id") id: Int): Call<User>

    fun amina(): Call<ResponseBody>

}