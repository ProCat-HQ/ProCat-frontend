package com.example.procatfirst.repository.data_storage

import android.content.Context
import android.util.Log
import com.example.procatfirst.data.Tool
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
    private var filesDir: File? = null

    fun getUserCartFromMemory(): MutableList<Tool> {
        var rowJsonUserCart = "{}"
        try {
            rowJsonUserCart = File(filesDir, FILEPATH).readText()
        } catch (e: FileNotFoundException) {
            File(filesDir, FILEPATH).createNewFile()
        }
        val json = Json { prettyPrint = true }
        val result: MutableList<Tool> = try {
            json.decodeFromString(rowJsonUserCart)
        } catch (e: RuntimeException) {
            mutableListOf()
        }
        return result
    }

    fun setUserCartToMemory(cart: MutableList<Tool>) {
        val json = Json { prettyPrint = true }
        if(!(File(filesDir, FILEPATH).exists())) {
            File(filesDir, FILEPATH).createNewFile()
        }
        try {
            File(filesDir, FILEPATH).writeText(json.encodeToString(cart))
        } catch (e: RuntimeException) {
            Log.i(
                identifier,
                "Error to write data into file"
            )
        }
    }

    fun initialize(con: Context) {
        this.context = con
        filesDir = con.applicationContext.filesDir
    }



}