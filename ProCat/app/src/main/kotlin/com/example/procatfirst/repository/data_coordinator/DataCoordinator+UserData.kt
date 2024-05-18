package com.example.procatfirst.repository.data_coordinator

import android.content.Context
import android.util.Log
import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.data.User
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.api.JwtToken
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_storage.DataStorage
import com.example.procatfirst.repository.data_storage.getUserDataFromMemory
import com.example.procatfirst.repository.data_storage.setUserDataToMemory
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import com.example.procatfirst.repository.data_storage_deprecated.setRefreshTokenDataStore
import com.example.procatfirst.repository.data_storage_deprecated.updateRefreshToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    val decoded = JSONObject(decodeToken(token).payload)
    UserDataCache.shared.setUserRole(decoded.getString("userRole"))
    UserDataCache.shared.setUserData(User(decoded.getString("userId").toInt(), "", "", "", "", false, decoded.getString("userRole"), "", "" ))

}

suspend fun DataCoordinator.setTokenAndRole(token : String, refresh : String, context: Context)  {
    Log.v("TOKEN", token)
    DataCoordinatorOLD.shared.updateRefreshToken(value = refresh, context = context)
    UserDataCache.shared.setUserToken(token)
    setUserRole(token)
    //Чтобы корзина работала до её открытия (когда добавляем инструмент)
    withContext(Dispatchers.IO) {
        ApiCalls.shared.getCartApi { payload: CartPayload -> UserCartCache.shared.setUserCartStuff(payload) }
    }

}