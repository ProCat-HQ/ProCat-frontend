package com.example.procatfirst.repository.api

import android.util.Log
import com.example.procatfirst.data.Store
import com.example.procatfirst.data.StoreResponse
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

class StoreApiCalls {
    companion object {
        val shared = StoreApiCalls()
        const val BACKEND_URL = "http://79.137.205.181:8081"
        const val identifier = "[ApiCalls]"
    }


    fun getAllStoresApi(callback : (String, List<Store>) -> Unit) {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(StoresService::class.java)

        service.getAllStores().enqueue(object : Callback<StoreResponse> {

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                t.printStackTrace()
                Log.i("API", t.toString())
            }

            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                Log.i("RESPONSE", response.raw().toString())
                response.body()?.let {
                    Log.d("STORES_RESULT", it.payload.toString())
                    callback("SUCCESS", it.payload) }
            }

        })
    }


    fun updateStoreApi(storeId: Int, name: String, address: String, workingHoursStart: String, workingHoursEnd: String, callback: (String) -> Unit) {
        val url = BACKEND_URL
        val service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(StoresService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("address", address)
        jsonObject.put("workingHoursStart", workingHoursStart)
        jsonObject.put("workingHoursEnd", workingHoursEnd)

        Log.d("WHS", workingHoursStart)

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        service.updateStore("Bearer " + UserDataCache.shared.getUserToken(), storeId, requestBody).enqueue(object :
            Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                //Log.i("API", t.toString())
                Log.i("API_ERROR", call.toString())

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.i("RESPONSE", response.raw().toString())
                    response.body()?.let {
                        callback("SUCCESS")
                    }
                } else {
                    Log.i("RESPONSE_ERROR", response.errorBody()?.string().orEmpty())
                    callback("FAILURE: ${response.errorBody()?.string().orEmpty()}")
                }
            }
        })
    }


}