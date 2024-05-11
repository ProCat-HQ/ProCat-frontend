package com.example.procatfirst.repository.api

data class UserResponse(
    val email: String,
    val phone: String,
    val id: String,
    val fullName: String,
)

data class TokenResponse(
    val status: Int,
    val message: String,
    val payload: Token,
)

data class Token(
    val token: String,
)

data class JwtToken(
    val header: String,
    val payload: String,
)