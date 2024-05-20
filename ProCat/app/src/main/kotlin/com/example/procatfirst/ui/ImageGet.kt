package com.example.procatfirst.ui

import okhttp3.ResponseBody
import retrofit2.http.GET

interface FileDownloadApi {
    @GET("assets/hammer.jpg")
    suspend fun downloadZipFile(): ResponseBody
}



