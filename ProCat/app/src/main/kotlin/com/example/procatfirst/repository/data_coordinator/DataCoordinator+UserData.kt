package com.example.procatfirst.repository.data_coordinator

import android.service.autofill.UserData
import com.example.procatfirst.data.User
import com.example.procatfirst.repository.UserRoleRepository
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_storage.DataStorage
import com.example.procatfirst.repository.data_storage.getUserDataFromMemory
import com.example.procatfirst.repository.data_storage.setUserDataToMemory
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

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

fun DataCoordinator.getUserRole() : String {

    return UserDataCache.shared.getUserRole()

}

suspend fun DataCoordinator.setUserRole()  {

    val userRole = UserRoleRepository.shared.getUserRole().first()
    UserDataCache.shared.setUserRole(userRole)

}