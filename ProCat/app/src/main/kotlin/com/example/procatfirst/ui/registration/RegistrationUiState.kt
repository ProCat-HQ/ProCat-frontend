package com.example.procatfirst.ui.registration

data class RegistrationUiState(
    val phoneNumber: String = "89000000009",
    val password: String = "",
    val lastName: String = "",
    val firstName: String = "",
    val fatherName: String = "",
    val enteredPasswordWrong: Boolean = false,
    val enteredPhoneNumberWrong: Boolean = false,
    val enteredFirstNameWrong: Boolean = false,
    val enteredLastNameWrong: Boolean = false,
    val enteredFatherNameWrong: Boolean = false,
    )
