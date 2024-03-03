package com.example.procatfirst.data

import com.example.procatfirst.R
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: Int,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val identificationNumber: String,
    val isConfirmed: Boolean,
    val role: String
)

object UserDataProvider {
    val users = listOf(
        User(
            userId = 1,
            fullName = "Vasilisa Vas",
            email = "email@gmail.com",
            phoneNumber = "89000000000",
            identificationNumber = "00000000",
            isConfirmed = false,
            role = "user"
        ),
        User(
            userId = 2,
            fullName = "Nastya Nas",
            email = "email@gmail.com",
            phoneNumber = "89000000000",
            identificationNumber = "00000000",
            isConfirmed = true,
            role = "user"
        ),
        User(
            userId = 3,
            fullName = "Olya Aloy",
            email = "email@gmail.com",
            phoneNumber = "89000000000",
            identificationNumber = "00000000",
            isConfirmed = false,
            role = "admin"
        ),
        User(
            userId = 4,
            fullName = "Charlie Morning",
            email = "email@gmail.com",
            phoneNumber = "89000000000",
            identificationNumber = "00000000",
            isConfirmed = false,
            role = "courier"
        ),
    )
}