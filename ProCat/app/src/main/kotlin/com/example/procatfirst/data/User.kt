package com.example.procatfirst.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
        val id: Int,
        val fullName: String,
        val email: String,
        val phoneNumber: String,
        val identificationNumber: String,
        val isConfirmed: Boolean,
        val role: String,
        val created_at : String,
        val password_hash : String,
)

object UserDataProvider {
    val users = listOf(
        User(
            id = 1,
            fullName = "Vasilisa Vas",
            email = "email@gmail.com",
            phoneNumber = "89000000000",
            identificationNumber = "00000000",
            isConfirmed = false,
            role = "user",
            created_at = "",
            password_hash = "",
        ),
        User(
            id = 2,
            fullName = "Nastya Nas",
            email = "email@gmail.com",
            phoneNumber = "89000000000",
            identificationNumber = "00000000",
            isConfirmed = true,
            role = "user",
                created_at = "",
                password_hash = "",
        ),
        User(
            id = 3,
            fullName = "Olya Aloy",
            email = "email@gmail.com",
            phoneNumber = "89000000000",
            identificationNumber = "00000000",
            isConfirmed = false,
            role = "admin",
                created_at = "",
                password_hash = "",
        ),
        User(
            id = 4,
            fullName = "Charlie Morning",
            email = "email@gmail.com",
            phoneNumber = "89000000000",
            identificationNumber = "00000000",
            isConfirmed = false,
            role = "courier",
                created_at = "",
                password_hash = "",
        ),
    )
}