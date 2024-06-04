package com.example.procatfirst.repository.api

data class TokenResponse(
    val status: Int,
    val message: String,
    val payload: Tokens,
)

data class Tokens(
    val accessToken: String,
    val refreshToken: String,
)

data class JwtToken(
    val header: String,
    val payload: String,
)