package com.example.procatfirst.repository.api

import android.util.Log
import com.example.procatfirst.data.ClusterPayload
import com.example.procatfirst.data.ClusterResult
import com.example.procatfirst.data.ItemResponse
import com.example.procatfirst.data.User
import com.example.procatfirst.data.UserDataResponse
import com.example.procatfirst.data.UserResponse
import com.example.procatfirst.repository.cache.CatalogCache
import com.example.procatfirst.repository.cache.AllOrdersCache
import com.example.procatfirst.repository.cache.UserDataCache
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
                //response.body()?.let { UserDataCache.shared.setUserData(it) }
                //response.body()?.let { UserDataCache.shared.setUserData(User(it.payload.id, it.payload.fullName, it.payload.email, it.payload.phoneNumber, it.payload.identificationNumber, it.payload.isConfirmed, it.payload.role, it.payload.created_at, "hash")) }
                response.body()?.let {
                    Log.d("USER_DATA", it.payload.toString())
                    callback("SUCCESS", it.payload) }
                //Log.i("UserData", UserDataCache.shared.getUserData().toString())
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
                callback("FAIL", "none", "none")
            }
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.code() == 200) {
                    response.body()?.let {
                        callback("SUCCESS", it.payload.accessToken, it.payload.refreshToken)
                    }
                }
                else {
                    callback("FAKE", "none", "none")
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
                callback("FAIL", "none", "none")
            }
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.code() == 200) {
                    response.body()?.let {
                        //Log.i("TOKEN Response", it.payload.token)
                        callback("SUCCESS", it.payload.accessToken, it.payload.refreshToken)
                    }
                }
                else {
                    callback("FAIL", "none", "none")
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

        service.logout("Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //pofig
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //toshe pofig
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

    suspend fun makeCluster(callback: (String, List<ClusterResult>) -> Unit) {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(UserService::class.java)

        Log.d("USER_TOKEN", UserDataCache.shared.getUserToken())
        service.makeCluster("Bearer " + UserDataCache.shared.getUserToken()).enqueue(object : Callback<ClusterPayload> {

            override fun onFailure(call: Call<ClusterPayload>, t: Throwable) {
                t.printStackTrace()
                Log.i("API", t.toString())
            }

            override fun onResponse(call: Call<ClusterPayload>, response: Response<ClusterPayload>) {
                Log.i("RESPONSE", response.raw().toString())
                response.body()?.let {
                    Log.d("Cluster result", it.result.toString())
                    callback("SUCCESS", it.result)
                }
            }

        })
    }


}