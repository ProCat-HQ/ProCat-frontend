package com.example.procatfirst.repository.api

import com.example.procatfirst.BuildConfig
import com.example.procatfirst.data.AllDeliveriesForDeliverymanResponse
import com.example.procatfirst.data.AllDeliveriesToSortResponse
import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.data.CartResponse
import com.example.procatfirst.data.ChangeDeliveryRequest
import com.example.procatfirst.data.ClusterPayload
import com.example.procatfirst.data.ClusterResponse
import com.example.procatfirst.data.Delivery
import com.example.procatfirst.data.DeliveryPayload
import com.example.procatfirst.data.DeliveryResponse
import com.example.procatfirst.data.Deliveryman
import com.example.procatfirst.data.DeliverymenPayload
import com.example.procatfirst.data.DeliverymenResponse
import com.example.procatfirst.data.ItemResponse
import com.example.procatfirst.data.ItemsResponse
import com.example.procatfirst.data.NewOrderResponse
import com.example.procatfirst.data.Notification
import com.example.procatfirst.data.NotificationItem
import com.example.procatfirst.data.NotificationPayload
import com.example.procatfirst.data.Order
import com.example.procatfirst.data.OrderFull
import com.example.procatfirst.data.OrderRequest
import com.example.procatfirst.data.OrdersPayload
import com.example.procatfirst.data.OrdersResponse
import com.example.procatfirst.data.PaymentPayload
import com.example.procatfirst.data.RegistrationResponse
import com.example.procatfirst.data.RoutePayload
import com.example.procatfirst.data.SimpleDeliveryman
import com.example.procatfirst.data.SubscriptionResponse
import com.example.procatfirst.data.User
import com.example.procatfirst.data.UserResponse
import com.example.procatfirst.data.UsersResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/* Retrofit service that maps the different endpoints on the API, you'd create one
 * method per endpoint, and use the @Path, @Query and other annotations to customize
 * these at runtime */

interface UserService {

    @GET("/users")
    fun getAllUsers(@Header("Authorization") token: String?, @Query("limit") limit: Int? = null, @Query("page") page: Int? = null, @Query("role") role: String? = null): Call<UsersResponse>

    //@GET("/users/{userId}")
    //suspend fun getSimpleUser(@Query("userId") userId: Int): Call<ResponseBody>

    @DELETE("/users/{userId}")
    fun deleteUser(@Path("userId") userId: Int, @Header("Authorization") token: String?): Call<ResponseBody>

    @POST("/users/sign-up")
    fun register(@Body requestBody: RequestBody): Call<RegistrationResponse>

    @POST("/users/sign-in")
    fun login(@Body requestBody: RequestBody): Call<TokenResponse>

    @POST("/users/verification/send")
    fun createNewVerificationCode(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/verification/check")
    suspend fun checkCode(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("users/verification/iin")
    suspend fun postIin(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/iin-bin")
    fun changeIin(@Body requestBody: RequestBody, @Header("Authorization") token: String?): Call<ResponseBody>

    @POST("users/change/fullname")
    fun changeFullName(@Body requestBody: RequestBody, @Header("Authorization") token: String?): Call<ResponseBody>

    @POST("/users/change/password")
    suspend fun changePassword(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/phone")
    suspend fun changePhone(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("/users/change/email")
    fun changeEmail(@Body requestBody: RequestBody, @Header("Authorization") token: String?): Call<ResponseBody>

    @POST("users/change/role/{userId}")
    suspend fun changeRole(@Query("userId") userId: Int, @Body requestBody: RequestBody)

    @GET("/users/deliverymen")
    fun getAllDeliverymen(@Header("Authorization") token: String?): Call<DeliverymenResponse>

    @GET("/users/deliverymen/{deliveryId}")
    suspend fun getDeliveryMan(@Query("deliveryId") deliveryId: Int): Call<SimpleDeliveryman>

    @POST("/users/deliverymen/{userId}")
    fun createDeliveryManFromUser(@Header("Authorization") token: String?, @Path("userId") userId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @PATCH("/users/deliverymen/{deliverymanId}")
    suspend fun changeDeliverymanData(@Query("deliveryId") deliveryId: Int, @Body requestBody: SimpleDeliveryman)

    @DELETE("/users/deliverymen/{deliverymanId}")
    fun deleteDeliveryman(@Header("Authorization") token: String?, @Path("deliverymanId") deliveryId: Int): Call<ResponseBody>

    @GET("/users/deliverymen/deliveries/")
    suspend fun getAllDeliveries(): Call<DeliveryPayload>

    @GET("/users/deliverymen/deliveries/{deliverymanId}")
    fun getDeliveriesForDeliveryman(@Header("Authorization") token: String?, @Path("deliverymanId") deliverymanId: Int): Call<AllDeliveriesForDeliverymanResponse>

    @GET("/users/deliverymen/deliveries/delivery/{deliveryId}")
    fun getDeliveryFromDeliveryId(@Header("Authorization") token: String?, @Path("deliveryId") deliveryId: Int): Call<DeliveryResponse>

    //TODO
    @PATCH("/users/deliverymen/deliveries/{deliveryId}")
    suspend fun changeStatusForDeliveryFromDeliveryId(@Header("Authorization") token: String?, @Path("deliveryId") deliveryId: Int, requestBody: RequestBody): Call<ResponseBody>


    @POST("/users/deliverymen/deliveries/create-route")
    suspend fun createRouteFromDeliveryman(@Body requestBody: RequestBody): Call<RoutePayload>

    @POST("/users/admin/cluster")
    fun makeCluster(@Header("Authorization") token: String?): Call<ClusterResponse>

    @GET("/users/admin/deliveries-to-sort")
    fun getAllDeliveriesToSort(@Header("Authorization") token: String?): Call<AllDeliveriesToSortResponse>

    @PATCH("/users/admin/change-delivery")
    fun changeDelivery(@Header("Authorization") token: String?, @Body requestBody: RequestBody): Call<ResponseBody>

    @GET("/users/cart")
    fun getItemsInCart(@Header("Authorization") token: String?): Call<CartResponse>

    @POST("users/cart")
    fun postCart(@Body requestBody: RequestBody, @Header("Authorization") token: String?): Call<ResponseBody>

    @DELETE("/users/cart/{itemId}")
    fun deleteItemFromCart(@Path("itemId") id : Int, @Query("count") count : Int, @Header("Authorization") token: String?): Call<ResponseBody>

    @GET("/users/orders")
    fun getAllOrders(@Header("Authorization") token: String?): Call<OrdersResponse>

    @GET("/users/orders")
    fun getUserOrders(@Query("userId") id : Int, @Header("Authorization") token: String?): Call<OrdersResponse>

    @GET("/users/orders/{orderId}")
    suspend fun getOrder(@Path("orderId", ) orderId: Int): Call<OrderFull>

    @POST("/users/orders")
    fun createNewOrder(@Body requestBody: RequestBody, @Header("Authorization") token: String?): Call<NewOrderResponse>

    @PATCH("/users/orders/cancel/{orderId}")
    fun cancelOrder(@Header("Authorization") token: String?, @Path("orderId") orderId: Int): Call<ResponseBody>

    @PATCH("/users/orders/status/{orderId}")
    fun changeStatusOfOrder(@Header("Authorization") token: String?, @Path("orderId") orderId: Int, @Body requestBody: RequestBody): Call<ResponseBody>

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

    @GET("/users/{userId}")
    fun getUser(@Header("Authorization") token: String?, @Path("userId") userId: Int): Call<UserResponse>

    @POST("/users/refresh")
    fun refresh(@Body requestBody: RequestBody): Call<TokenResponse>

    @POST("/users/logout")
    fun logout(@Header("Authorization") token: String?, @Body requestBody: RequestBody): Call<ResponseBody>

    suspend fun amina(): Call<ResponseBody>

}