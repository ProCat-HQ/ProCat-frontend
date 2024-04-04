package com.example.procatfirst.repository.api

data class UserResponse(
    val email: String,
    val phone: String,
    val id: String,
    val fullName: String,

)

data class TokenResponse(
        val token: String,
)