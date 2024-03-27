package com.example.procatfirst.repository.data_coordinator

import com.example.procatfirst.data.User
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_storage.DataStorage
import com.example.procatfirst.repository.data_storage.getUserDataFromMemory
import com.example.procatfirst.repository.data_storage.setUserDataToMemory

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