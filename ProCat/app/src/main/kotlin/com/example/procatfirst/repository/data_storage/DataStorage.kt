package com.example.procatfirst.repository.data_storage

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.procatfirst.data.ToolWithCnt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException

class DataStorage {

    companion object {
        val shared = DataStorage()
        const val identifier = "[DataStorage]"
    }

    private val FILEPATH = "user_cart.json"
    private var context: Context? = null
    var filesDir: File? = null

    suspend fun getUserCartFromMemory(): MutableMap<Int, ToolWithCnt> {
        var rowJsonUserCart = "{}"
        try {
            rowJsonUserCart = File(filesDir, FILEPATH).readText()
        } catch (e: FileNotFoundException) {
            withContext(Dispatchers.IO) {
                File(filesDir, FILEPATH).createNewFile()
            }
        }
        val json = Json { prettyPrint = true }
        val result: MutableMap<Int, ToolWithCnt> = try {
            json.decodeFromString(rowJsonUserCart)
        } catch (e: RuntimeException) {
            HashMap()
        }
        return result
    }

    suspend fun setUserCartToMemory(cart: Map<Int, ToolWithCnt>) {
        Log.d("CART_DATA", cart.toString())
        val json = Json { prettyPrint = true }
        if(!(File(filesDir, FILEPATH).exists())) {
            withContext(Dispatchers.IO) {
                File(filesDir, FILEPATH).createNewFile()
            }
        }
        try {
            File(filesDir, FILEPATH).writeText(json.encodeToString(cart))
        } catch (e: RuntimeException) {
            Log.e(
                identifier,
                "Error to write data into file: " + e.message
            )
        }
    }

    suspend fun getImages(): Bitmap? {
        var rowJsonUserCart = "{}"
        try {
            rowJsonUserCart = File(filesDir, FILEPATH).readText()
        } catch (e: FileNotFoundException) {
            withContext(Dispatchers.IO) {
                File(filesDir, FILEPATH).createNewFile()
            }
        }
        val json = Json { prettyPrint = true }
        val result: Bitmap? = try {
            json.decodeFromString(rowJsonUserCart)
        } catch (e: RuntimeException) {
            null
        }
        return result
    }

    suspend fun setImages(bitmap: Bitmap) {
        val json = Json { prettyPrint = true }
        if(!(File(filesDir, FILEPATH).exists())) {
            withContext(Dispatchers.IO) {
                File(filesDir, FILEPATH).createNewFile()
            }
        }
        try {
            File(filesDir, FILEPATH).writeText(json.encodeToString(bitmap))
        } catch (e: RuntimeException) {
            Log.e(
                identifier,
                "Error to write data into file: " + e.message
            )
        }
    }

    fun initialize(con: Context) {
        this.context = con
        filesDir = con.applicationContext.filesDir

        Log.i(
            identifier,
            "data storage init"
        )

    }

}