package com.example.procatfirst.repository.api

import android.util.Log
import com.example.procatfirst.repository.cache.CatalogCache
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

    fun getItems() {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        service.getItems().enqueue(object : Callback<ItemsResponse> {

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
                response.body()?.let { CatalogCache.shared.addCatalogStuff(it.items) }

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
                    //it.
                    Log.i("RESPONSE", it.token)
                }

            }

        })

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
                response.body()?.string()?.let { Log.i("RESPONSE", it) }
            }

        })

    }

}








