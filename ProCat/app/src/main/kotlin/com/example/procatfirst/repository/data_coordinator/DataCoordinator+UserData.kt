package com.example.procatfirst.repository.data_coordinator

import android.util.Log
import com.example.procatfirst.data.User
import com.example.procatfirst.repository.api.JwtToken
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_storage.DataStorage
import com.example.procatfirst.repository.data_storage.getUserDataFromMemory
import com.example.procatfirst.repository.data_storage.setRefresh
import com.example.procatfirst.repository.data_storage.setUserDataToMemory
import org.json.JSONObject
import java.util.Base64

suspend fun DataCoordinator.getUserData() : User {

    if (UserDataCache.shared.getUserData() == null) {
        UserDataCache.shared.setUserData(DataStorage.shared.getUserDataFromMemory())
    }
    return UserDataCache.shared.getUserData()!!

}

suspend fun DataCoordinator.setUserData(user: User)  {

    UserDataCache.shared.setUserData(user)
    DataStorage.shared.setUserDataToMemory(user)

}

private fun decodeToken(jwt: String): JwtToken {
    val parts = jwt.split(".")
    return try {
        val charset = charset("UTF-8")
        val header = String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
        val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
        JwtToken(header, payload)
    } catch (e: Exception) {
        Log.e("JWT", "Error parsing JWT: $e")
        JwtToken("?", "?")
    }
}

fun DataCoordinator.getUserRole() : String {

    return UserDataCache.shared.getUserRole()

}

fun DataCoordinator.setUserRole(token : String)  {

    val decoded = decodeToken(token)
    UserDataCache.shared.setUserRole(JSONObject(decoded.payload).getString("userRole"))

}

suspend fun DataCoordinator.setTokenAndRole(token : String, refresh : String)  {

    DataStorage.shared.setRefresh(refresh)
    UserDataCache.shared.setUserToken(token)
    setUserRole(token)

}