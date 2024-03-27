package com.example.procatfirst.repository.cache

import com.example.procatfirst.data.User

class UserDataCache {

    var user : User? = null

    companion object {
        val shared = UserDataCache()
        const val identifier = "[UserCartCache]"
    }

    fun getUserData() : User? {
        return user
    }

    fun setUserData(userData : User) {
        user = userData
    }

}