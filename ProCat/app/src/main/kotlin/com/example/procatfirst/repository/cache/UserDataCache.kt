package com.example.procatfirst.repository.cache

import com.example.procatfirst.data.User

class UserDataCache {

    var user : User? = null
    var role : String = "User"

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

    fun getUserRole() : String {
        return role
    }

    fun setUserRole(userRole : String) {
        role = userRole
    }

}