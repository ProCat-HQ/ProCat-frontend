package com.example.procatfirst.ui.personal.profile

data class ProfileUiState(
    val userId: Int = 0,
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val identificationNumber: String = "",
    val isConfirmed: Boolean = false,
    val role: String = "user",
    val errorDialog: Boolean = false,
    val errorMessage: String = "",
    val passwordDialog: Boolean = false,

    val password: String = "",

)