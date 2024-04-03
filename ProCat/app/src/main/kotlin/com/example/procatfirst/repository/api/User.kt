package com.example.procatfirst.repository.api

data class User(
    val email: String,
    val phone: String
)

data class TokenResponse(
        val token: String,
)