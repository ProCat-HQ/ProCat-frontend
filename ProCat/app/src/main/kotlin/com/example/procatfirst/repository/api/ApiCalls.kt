package com.example.procatfirst.repository.api

import android.util.Log
import android.widget.Toast
import com.example.procatfirst.R
import com.example.procatfirst.data.ApiResponseDelivery
import com.example.procatfirst.data.Delivery
import com.example.procatfirst.data.DeliveryMan
import com.example.procatfirst.data.Tool
import com.example.procatfirst.data.User
import com.example.procatfirst.repository.cache.CatalogCache
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.cache.AllOrdersCache
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.setUserData
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

/**
 * Тут из полезного пока только getItems() - делает GET запрос, результат пишет в кэш.
 * Есть ещё postApi() - шлёт данные по указанному url, но пока не понятно, работает ли оно вообще.
 * Короче над Api ещё много работы.
 */
class ApiCalls {
    companion object {
        val shared = ApiCalls()
        const val BACKEND_URL = "http://10.0.2.2:8080"
        const val identifier = "[ApiCalls]"
    }

    fun getItemsApi1() {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)


        service.getItems("Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<ItemsResponse> {

            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<ItemsResponse>, t: Throwable) {
                t.printStackTrace()
                Log.i("API", t.toString())
                //!!!! TODO error intent !!!!
                NotificationCoordinator.shared.sendIntent(SystemNotifications.stuffAddedIntent)
            }

            /* The HTTP call was successful, we should still check status code and response body
             * on a production app. This method is run on the main thread */
            override fun onResponse(call: Call<ItemsResponse>, response: Response<ItemsResponse>) {
                Log.i("RESPONSE", response.raw().toString())
                /* This will print the response of the network call to the Logcat */
                // TODO вот здесь похоже на нарушение архитектуры (нижний слой обращается к вернему)
                response.body()?.let { CatalogCache.shared.addCatalogStuff(it.payload) }

            }

        })
    }

    fun getItemsApi() {
        val t1 = Item(1, "Молоток", R.drawable.hammer, "Надёжный, качественный", "Масса: 0.4 кг", 350)
        val t2 = Item(1, "Набор", R.drawable.set, "Практичный, прочный", "Масса: 0.45 кг", 800)
        val toolsList = listOf(t1, t2)
        CatalogCache.shared.addCatalogStuff(toolsList)
    }

    fun getItemsApi2() {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)


        service.getItems1("Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<ResponseBody> {

            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                Log.i("API", t.toString())
                //!!!! TODO error intent !!!!
                NotificationCoordinator.shared.sendIntent(SystemNotifications.stuffAddedIntent)
            }

            /* The HTTP call was successful, we should still check status code and response body
             * on a production app. This method is run on the main thread */
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("RESPONSE", response.raw().toString())
                /* This will print the response of the network call to the Logcat */
                // TODO вот здесь похоже на нарушение архитектуры (нижний слой обращается к вернему)
                response.body()?.string()?.let { Log.i("RESPONSE", it) }
            }

        })
    }

    fun getUserDataApi(id : Int) {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)


        service.getUser(id).enqueue(object : Callback<User> {

            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
                Log.i("API", t.toString())
                //!!!! TODO error intent !!!!

            }

            /* The HTTP call was successful, we should still check status code and response body
             * on a production app. This method is run on the main thread */
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.i("RESPONSE", response.raw().toString())
                /* This will print the response of the network call to the Logcat */
                // TODO вот здесь похоже на нарушение архитектуры (нижний слой обращается к вернему)
                response.body()?.let { UserDataCache.shared.setUserData(it) }
                Log.i("UserData", UserDataCache.shared.getUserData().toString())
            }

        })
    }

    fun aminaApi() {
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

    public fun signUpApi(login: String, password: String, name: String)  {

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

    public fun signInApi(login: String, password: String)  {

        val service = Retrofit.Builder()
                .baseUrl(BACKEND_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)

        /* Calls the endpoint set on getUsers (/api) from UserService using enqueue method
         * that creates a new worker thread to make the HTTP call */

        val jsonObject = JSONObject()
        jsonObject.put("phoneNumber", login)
        jsonObject.put("password", password)

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        service.login(requestBody).enqueue(object : Callback<TokenResponse> { //ResponseBody

            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                t.printStackTrace()
                Log.i("RESPONSE", "Fail")
            }

            /* The HTTP call was successful, we should still check status code and response body
             * on a production app. This method is run on the main thread */
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                /* This will print the response of the network call to the Logcat */
                response.body()?.let {
                    UserDataCache.shared.setUserToken(it.token)
                    Log.i("RESPONSE", it.token)
                }

            }

        })

    }

    public fun courierDistributionApi()  {

        val service = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        val jsonObject = JSONObject()
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        //-----------------------
        val deliveries = listOf(
            Delivery(54.849023, 83.109914, 105),
            Delivery(54.864174, 83.092518, 106),
            Delivery(54.850213, 83.046704, 107),
            Delivery(54.837411, 83.112056, 108)
        )

        val deliveryMan1 = DeliveryMan(1, deliveries)

        val deliveries2 = listOf(
            Delivery(55.04868, 82.988786, 101),
            Delivery(54.98254, 82.814378, 102),
            Delivery(54.96244, 82.885103, 103),
            Delivery(54.988017, 83.015966, 104)
        )

        val deliveryMan2 = DeliveryMan(2, deliveries2)

        val apiResponse = ApiResponseDelivery("ok", listOf(deliveryMan1, deliveryMan2))

        val jsonObject2 = JSONObject(apiResponse.toString())
        //-------------------

    }




    public fun geocoderApi()  {

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

}