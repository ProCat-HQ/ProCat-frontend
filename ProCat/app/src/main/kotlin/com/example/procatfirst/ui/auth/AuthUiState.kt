package com.example.procatfirst.ui.auth

data class AuthUiState(
    val phoneNumber: String = "",
    val password: String = "",
    val enteredPasswordWrong: Boolean = false,
    val enteredPhoneNumberWrong: Boolean = false
)
