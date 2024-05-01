package com.example.procatfirst.repository.api

import com.example.procatfirst.BuildConfig
import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.data.ChangeDeliveryRequest
import com.example.procatfirst.data.ClusterPayload
import com.example.procatfirst.data.Delivery
import com.example.procatfirst.data.DeliveryPayload
import com.example.procatfirst.data.Deliveryman
import com.example.procatfirst.data.Notification
import com.example.procatfirst.data.NotificationItem
import com.example.procatfirst.data.NotificationPayload
import com.example.procatfirst.data.OrderFull
import com.example.procatfirst.data.OrderRequest
import com.example.procatfirst.data.OrdersPayload
import com.example.procatfirst.data.PaymentPayload
import com.example.procatfirst.data.RoutePayload
import com.example.procatfirst.data.SimpleDeliveryman
import com.example.procatfirst.data.SubscriptionResponse
import com.example.procatfirst.data.User
import com.example.procatfirst.data.UsersResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

/* Retrofit service that maps the different endpoints on the API, you'd create one
 * method per endpoint, and use the @Path, @Query and other annotations to customize
 * these at runtime */

interface UserService {

    @GET("/users")
    suspend fun getAllUsers(@Query("limit") limit: Int? = null, @Query("page") page: Int? = null, @Query("role") role: String? = null): UsersResponse

    @GET("/users/{userId}")
    suspend fun getSimpleUser(@Query("userId") userId: Int): Call<ResponseBody>

    @DELETE("/users/{userId}")
    suspend fun deleteUser(@Query("userId") userId: Int)

    @POST("/users/sign-up")
    fun register(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/sign-in")
    fun login(@Body requestBody: RequestBody): Call<TokenResponse>

    @POST("/users/verification/send")
    fun createNewVerificationCode(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/verification/check")
    suspend fun checkCode(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("users/verification/iin")
    suspend fun postIin(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/iin-bin")
    suspend fun changeIin(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("users/change/fullname")
    suspend fun changeFullName(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/password")
    suspend fun changePassword(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/phone")
    suspend fun changePhone(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/email")
    suspend fun changeEmail(@Body requestBody: RequestBody)

    @POST("users/change/role/{userId}")
    suspend fun changeRole(@Query("userId") userId: Int, @Body requestBody: RequestBody)

    @GET("/users/deliverymen")
    suspend fun getAllDeliverymen(): Call<Deliveryman>

    @GET("/users/deliverymen/{deliveryId}")
    suspend fun getDeliveryMan(@Query("deliveryId") deliveryId: Int): Call<SimpleDeliveryman>

    @POST("/users/deliverymen/{userId}")
    suspend fun createDeliveryManFromUser(@Query("userId") userId: Int, @Body requestBody: SimpleDeliveryman)

    @PATCH("/users/deliverymen/{deliverymanId}")
    suspend fun changeDeliverymanData(@Query("deliveryId") deliveryId: Int, @Body requestBody: SimpleDeliveryman)

    @DELETE("/users/deliverymen/{deliverymanId}")
    suspend fun deleteDeliveryman(@Query("deliveryId") deliveryId: Int): Call<ResponseBody>

    @GET("/users/deliverymen/deliveries/")
    suspend fun getAllDeliveries(): Call<DeliveryPayload>

    @GET("/users/deliverymen/deliveries/{deliverymanId}")
    suspend fun getDeliveriesForDeliveryman(@Query("deliveryId") deliveryId: Int): Call<DeliveryPayload>

    @PATCH("/users/deliverymen/deliveries/{deliverymanId}")
    suspend fun changeStatusForDeliveryman(@Query("deliveryId") deliveryId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/deliverymen/deliveries/create-route")
    suspend fun createRouteFromDeliveryman(@Body requestBody: RequestBody): Call<RoutePayload>

    @POST("/users/admin/cluster")
    suspend fun makeCluster(): Call<ClusterPayload>

    @GET("/users/admin/deliveries-to-sort")
    suspend fun getAllDeliveriesAfterClustering(): Call<ClusterPayload>

    @PATCH("/users/admin/change-delivery")
    suspend fun changeDelivery(@Body requestBody: ChangeDeliveryRequest): Call<ResponseBody>

    @GET("/users/cart/")
    suspend fun getItemsInCart(): Call<CartPayload>

    @POST("users/cart")
    suspend fun postCart(@Body requestBody: RequestBody)

    @DELETE("/users/cart")
    suspend fun deleteItemFromCart(@Body requestBody: RequestBody)

    @GET("/users/orders/")
    suspend fun getOrders(): Call<OrdersPayload>

    @GET("/users/orders/{orderId}")
    suspend fun getOrder(@Query("orderId") orderId: Int): Call<OrderFull>

    @POST("/users/orders/")
    suspend fun createNewOrder(@Body requestBody: OrderRequest): Call<ResponseBody>

    @POST("/users/orders/cancel/{orderId}")
    suspend fun setStatusOfOrder(@Query("orderId") orderId: Int): Call<ResponseBody>

    @PATCH("/users/orders/status/{orderId}")
    suspend fun changeStatusOfOrder(@Query("orderId") orderId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/users/orders/payment/{orderId}")
    suspend fun getInfoAboutPayments(@Query("orderId") orderId: Int): Call<PaymentPayload>

    @PATCH("/users/orders/payment/{paymentId}")
    suspend fun updatePaymentInfo(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/users/subscriptions/")
    suspend fun getAllSubsForUser(): Call<SubscriptionResponse>

    @POST("/users/subscriptions/")
    suspend fun addItemIdToSubs(@Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/users/notifications/")
    suspend fun getAllNotifications(): Call<NotificationPayload>

    @POST("/users/notifications/{usersId}")
    suspend fun sendNotification(@Query("usersId") usersId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @PATCH("/users/notifications/{notificationId}")
    suspend fun setNotificationToViewed(@Query("notificationId") notificationId: Int, @Body requestBody: NotificationItem): Call<ResponseBody>

    @DELETE("/users/notifications/{notificationId}")
    suspend fun deleteNotification(@Query("notificationId") notificationId: Int): Call<ResponseBody>

    @GET("geocode?q=Новосибирск, Пирогова 1&fields=items.point&key=${BuildConfig.apiKey}")
    fun geocoder(): Call<ResponseBody>

    @GET("/items")
    fun getItems(@Header("Authorization") token: String?): Call<ResponseBody>

    @GET("/users/{userId}")
    fun getUser(@Query("userId") userId: Int): Call<User>

    suspend fun amina(): Call<ResponseBody>

}