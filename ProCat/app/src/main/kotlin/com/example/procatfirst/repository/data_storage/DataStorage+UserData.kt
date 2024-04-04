package com.example.procatfirst.repository.data_storage

import android.util.Log
import com.example.procatfirst.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException

private const val FILEPATH = "user_data.json"

suspend fun DataStorage.getUserDataFromMemory() : User {

    var rowJson = "{}"
    try {
        rowJson = File(filesDir, FILEPATH).readText()
    } catch (e: FileNotFoundException) {
        withContext(Dispatchers.IO) {
            File(filesDir, FILEPATH).createNewFile()
        }
    }
    val json = Json { prettyPrint = true }
    val result: User = try {
        json.decodeFromString(rowJson)
    } catch (e: RuntimeException) {
        User(0, "?" ,"?", "?" ,"?", false, "?", "?", "?")
    }
    return result
}

suspend fun DataStorage.setUserDataToMemory(user : User) {
    val json = Json { prettyPrint = true }
    if(!(File(filesDir, FILEPATH).exists())) {
        withContext(Dispatchers.IO) {
            File(filesDir, FILEPATH).createNewFile()
        }
    }
    try {
        File(filesDir, FILEPATH).writeText(json.encodeToString(user))
    } catch (e: RuntimeException) {
        Log.i(
            DataStorage.identifier,
            "Error to write data into file"
        )
    }
}