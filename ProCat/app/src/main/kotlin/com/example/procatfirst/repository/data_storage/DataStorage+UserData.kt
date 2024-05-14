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
private const val SESSIONPATH = "session.json"

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

suspend fun DataStorage.setRefresh(token : String) {
    val json = Json { prettyPrint = true }
    if(!(File(filesDir, SESSIONPATH).exists())) {
        withContext(Dispatchers.IO) {
            File(filesDir, SESSIONPATH).createNewFile()
        }
    }
    try {
        File(filesDir, FILEPATH).writeText(json.encodeToString(token))
    } catch (e: RuntimeException) {
        Log.i(
            DataStorage.identifier,
            "Error to write token into file"
        )
    }
}

suspend fun DataStorage.getRefresh() : String {

    var rowJson = "{}"
    try {
        rowJson = File(filesDir, SESSIONPATH).readText()
    } catch (e: FileNotFoundException) {
        withContext(Dispatchers.IO) {
            File(filesDir, SESSIONPATH).createNewFile()
        }
    }
    val json = Json { prettyPrint = true }
    val result: String = try {
        json.decodeFromString(rowJson)
    } catch (e: RuntimeException) {
        "?"
    }
    return result
}