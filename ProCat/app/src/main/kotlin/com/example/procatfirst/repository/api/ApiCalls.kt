package com.example.procatfirst.repository.api

import android.util.Log
import com.example.procatfirst.data.AllDeliveriesToSortResponse
import com.example.procatfirst.data.ClusterPayload
import com.example.procatfirst.data.ClusterResult
import com.example.procatfirst.data.Deliveryman
import com.example.procatfirst.data.DeliverymenPayload
import com.example.procatfirst.data.DeliverymenResponse
import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.data.CartResponse
import com.example.procatfirst.data.ItemResponse
import com.example.procatfirst.data.OrdersPayload
import com.example.procatfirst.data.OrdersResponse
import com.example.procatfirst.data.User
import com.example.procatfirst.data.UserDataResponse
import com.example.procatfirst.data.UserResponse
import com.example.procatfirst.repository.cache.AllOrdersCache
import com.example.procatfirst.repository.cache.CatalogCache
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.dublgis.dgismobile.mapsdk.LonLat

class ApiCalls {
    companion object {
        val shared = ApiCalls()
        const val BACKEND_URL = "http://79.137.205.181:8081"
        const val identifier = "[ApiCalls]"
    }

    fun getItemsApi(callback : () -> Unit) {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)


        service.getItems().enqueue(object : Callback<ItemResponse> {

            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
                t.printStackTrace()
                Log.i("API", t.toString())
                callback()
            }

            /* The HTTP call was successful, we should still check status code and response body
             * on a production app. This method is run on the main thread */
            override fun onResponse(call: Call<ItemResponse>, response: Response<ItemResponse>) {
                Log.i("RESPONSE ROW", response.raw().toString())
                /* This will print the response of the network call to the Logcat */
                // TODO вот здесь похоже на нарушение архитектуры (нижний слой обращается к вернему)
                response.let {
                    Log.i("RESPONSE", it.toString())
                    CatalogCache.shared.addCatalogStuff(it.body()!!.payload.rows, callback)
                }

            }

        })
    }

    fun getUserDataApi(id: Int, callback: (String, UserDataResponse) -> Unit) {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)

        Log.d("CHECK!!!", id.toString() + " " + UserDataCache.shared.getUserToken())

        service.getUser("Bearer " + UserDataCache.shared.getUserToken(), id).enqueue(object : Callback<UserResponse> {

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.printStackTrace()
                Log.i("API", t.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.i("RESPONSE", response.raw().toString())
                response.body()?.let { UserDataCache.shared.setUserData(User(it.payload.id, it.payload.fullName, it.payload.email, it.payload.phoneNumber, it.payload.identificationNumber, it.payload.isConfirmed, it.payload.role, "", "hash")) }
                response.body()?.let {
                    Log.d("USER_DATA", it.payload.toString())
                    callback("SUCCESS", it.payload) }
            }

        })
    }


    fun signUpApi(login: String, password: String, name: String)  {

        val service = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        /* Calls the endpoint set on getUsers (/api) from UserService using enqueue method
         * that creates a new worker thread to make the HTTP call */

        val jsonObject = JSONObject()
        jsonObject.put("fullName", name)
        jsonObject.put("phoneNumber", login)
        jsonObject.put("password", password)

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        service.register(requestBody).enqueue(object : Callback<ResponseBody> { //ResponseBody

            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                Log.i("RESPONSE", "Fail")
            }

            /* The HTTP call was successful, we should still check status code and response body
             * on a production app. This method is run on the main thread */
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                /* This will print the response of the network call to the Logcat */
                response.body()?.string()?.let { Log.i("RESPONSE", it) }

            }

        })

    }

    suspend fun refresh(refresh: String, fingerprint: String, callback: (String, String, String) -> Unit) {
        val service = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("refreshToken", refresh)
        jsonObject.put("fingerprint", fingerprint)

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())
        service.refresh(requestBody).enqueue(object : Callback<TokenResponse> {
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                t.printStackTrace()
                Log.i("RESPONSE", "Fail")
                callback("FAIL", "", "")
            }
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.code() == 200) {
                    response.body()?.let {
                        callback("SUCCESS", it.payload.accessToken, it.payload.refreshToken)
                    }
                }
                else {
                    Log.d("API", response.raw().toString())
                    callback("FAKE", "", "")
                }
            }
        })
    }

    suspend fun signInApi(login: String, password: String, fingerprint: String, callback: (String, String, String) -> Unit) {

        val service = Retrofit.Builder()
                .baseUrl(BACKEND_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("phoneNumber", login)
        jsonObject.put("password", password)
        jsonObject.put("fingerprint", fingerprint)

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())
        service.login(requestBody).enqueue(object : Callback<TokenResponse> {
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                t.printStackTrace()
                Log.i("RESPONSE", "Fail")
                callback("FAIL", "", "")
            }
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.code() == 200) {
                    response.body()?.let {
                        //Log.i("TOKEN Response", it.payload.token)
                        callback("SUCCESS", it.payload.accessToken, it.payload.refreshToken)
                    }
                }
                else {
                    callback("FAIL", "", "")
                }
            }
        })

    }

    suspend fun logout() {
        val service = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("refreshToken", DataCoordinatorOLD.shared.refreshTokenPreferenceVariable)

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        service.logout("Bearer " + UserDataCache.shared.getUserToken(), requestBody).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("RESPONSE", response.raw().toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("RESPONSE", t.message.toString())
            }

        })
    }

    fun getCartApi(callback : (CartPayload) -> Unit) {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        service.getItemsInCart("Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<CartResponse> {

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("API", t.toString())
            }

            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                Log.i("RESPONSE ROW", response.raw().toString())
                response.let {
                    Log.i("RESPONSE", it.toString())
                    it.body()?.let { it1 -> callback(it1.payload) }
                }

            }

        })
    }

    fun postCart(itemId : Int, count: Int) {
        val service = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("itemId", itemId)
        jsonObject.put("count", count)

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        service.postCart( requestBody, "Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("RESPONSE", response.raw().toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("RESPONSE", t.message.toString())
            }

        })
    }

    fun deleteInCart(itemId : Int, cnt: Int) {
        val service = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        service.deleteItemFromCart( itemId, cnt, "Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("RESPONSE", response.raw().toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("RESPONSE", t.message.toString())
            }

        })
    }

    fun getUserOrders(id: Int, callback: (OrdersPayload?) -> Unit) {
        val service = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        service.getUserOrders(id, "Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<OrdersResponse> {
            override fun onResponse(call: Call<OrdersResponse>, response: Response<OrdersResponse>) {
                Log.d("RESPONSE", response.raw().toString())
                if (response.code() == 200) {
                    callback(response.body()?.payload)
                }
                else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<OrdersResponse>, t: Throwable) {
                Log.d("RESPONSE", t.message.toString())
                callback(null)
            }
        })

    }

    suspend fun getAllUsersApi()  {
        val service = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)
        // TODO can use Moshi
        try {
            val userResponse = service.getAllUsers()
            Log.i("RESPONSE", "Status: ${userResponse.status}, Message: ${userResponse.message}")
            for (user in userResponse.payload.rows) {
                Log.i("USER", "ID: ${user.id}, Name: ${user.fullName}, Email: ${user.email}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("RESPONSE", "Error: ${e.message}")
        }

    }

    suspend fun changeFullNameApi(fullName: String, password: String)  {
        val service = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("password", password)
        jsonObject.put("fullName", fullName)

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        service.changeFullName(requestBody).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                Log.i("RESPONSE", "Fail")
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.string()?.let { Log.i("RESPONSE", it) }
            }
        })

    }

    fun geocoderApi()  {

        val service = Retrofit.Builder()
            .baseUrl("https://catalog.api.2gis.com/3.0/items/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        /* Calls the endpoint set on getUsers (/api) from UserService using enqueue method
         * that creates a new worker thread to make the HTTP call */

        service.geocoder().enqueue(object : Callback<ResponseBody> { //ResponseBody

            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                Log.i("RESPONSE", "Fail")
            }

            /* The HTTP call was successful, we should still check status code and response body
             * on a production app. This method is run on the main thread */
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                /* This will print the response of the network call to the Logcat */
                response.body()?.string()?.let {

                    val lat = it.substring(it.indexOf("lat")+5, it.indexOf("lat")+14)
                    val lon = it.substring(it.indexOf("lon")+5, it.indexOf("lon")+14)
                    val coords = LonLat(lat.toDouble(), lon.toDouble())
                    AllOrdersCache.shared.setCoords(coords)
                    Log.i("RESPONSE lat", lat)
                    Log.i("RESPONSE lon", lon)
                }

            }

        })

    }

    suspend fun aminaApi() {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)


        service.amina().enqueue(object : Callback<ResponseBody> {

            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                Log.i("AMINA", t.toString())
            }

            /* The HTTP call was successful, we should still check status code and response body
             * on a production app. This method is run on the main thread */
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("RESPONSE", response.raw().toString())
                /* This will print the response of the network call to the Logcat */
                response.body()?.let { Log.d("AMINA", it.toString()) }

            }

        })
    }

    fun makeCluster(callback: (String, List<ClusterResult>) -> Unit) {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        service.makeCluster("Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<ClusterPayload> {

            override fun onFailure(call: Call<ClusterPayload>, t: Throwable) {
                t.printStackTrace()
                Log.i("API", t.toString())
            }

            override fun onResponse(call: Call<ClusterPayload>, response: Response<ClusterPayload>) {
                Log.i("RESPONSE", response.raw().toString())
                response.body()?.let {
                    callback("SUCCESS", it.result)
                }
            }

        })
    }

    fun getAllDeliverymenApi(callback : (String, List<Deliveryman>) -> Unit) {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        service.getAllDeliverymen("Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<DeliverymenResponse> {

            override fun onFailure(call: Call<DeliverymenResponse>, t: Throwable) {
                t.printStackTrace()
                Log.i("API", t.toString())
            }

            override fun onResponse(call: Call<DeliverymenResponse>, response: Response<DeliverymenResponse>) {
                Log.i("RESPONSE", response.raw().toString())
                response.body()?.let {
                    Log.d("DELIVERYMEN_COUNT", it.payload.count.toString())
                    callback("SUCCESS", it.payload.rows) }
            }

        })
    }

    fun getAllDeliveriesToSortApi(callback : (String, List<ClusterResult>) -> Unit) {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        service.getAllDeliveriesToSort("Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<AllDeliveriesToSortResponse> {

            override fun onFailure(call: Call<AllDeliveriesToSortResponse>, t: Throwable) {
                t.printStackTrace()
                Log.i("API", t.toString())
            }

            override fun onResponse(call: Call<AllDeliveriesToSortResponse>, response: Response<AllDeliveriesToSortResponse>) {
                Log.i("RESPONSE", response.raw().toString())
                response.body()?.let {
                    Log.d("DELIVERIES_COUNT", it.payload.count.toString())
                    Log.d("DELIVERIES_RESULT", it.payload.rows.toString())
                    callback("SUCCESS", it.payload.rows) }
            }

        })
    }


}