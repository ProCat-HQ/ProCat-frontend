package com.example.procatfirst.ui.personal.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.procatfirst.data.User
import com.example.procatfirst.data.UserDataProvider
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.ui.auth.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        getUserProfileInfo()
    }

    //Phone Number
    var userInputPhoneNumber by mutableStateOf(_uiState.value.phoneNumber)
        private set

    fun updateUserPhoneNumber(enteredPhoneNumber: String){
        userInputPhoneNumber = enteredPhoneNumber
    }

    fun saveUserPhoneNumber() {
        _uiState.update { currentState ->
            currentState.copy(phoneNumber = userInputPhoneNumber)
        }
        updateUserPhoneNumber(userInputPhoneNumber)
    }

    //FullName
    var userInputFullName by mutableStateOf(_uiState.value.fullName)
        private set

    fun updateUserFullName(enteredFullName: String){
        userInputFullName = enteredFullName
    }

    fun saveUserFullName() {
        _uiState.update { currentState ->
            currentState.copy(fullName = userInputFullName)
        }
        updateUserFullName(userInputFullName)
    }

    //IdentificationNumber
    var userInputIdentificationNumber by mutableStateOf(_uiState.value.identificationNumber)
        private set

    fun updateUserIdentificationNumber(enteredIdentificationNumber: String){
        userInputIdentificationNumber = enteredIdentificationNumber
    }

    fun saveUserIdentificationNumber() {
        _uiState.update { currentState ->
            currentState.copy(identificationNumber = userInputIdentificationNumber)
        }
        updateUserIdentificationNumber(userInputIdentificationNumber)
    }

    //Email
    var userInputEmail by mutableStateOf(_uiState.value.email)
        private set

    fun updateUserEmail(enteredEmail: String){
        userInputEmail = enteredEmail
    }

    fun saveUserEmail() {
        _uiState.update { currentState ->
            currentState.copy(email = userInputEmail)
        }
        updateUserEmail(userInputEmail)
    }



    private fun getUserProfileInfo() {
        val user = UserDataProvider.users[0];
        _uiState.value = _uiState.value.copy(
            userId = user.userId,
            fullName = user.fullName,
            email = user.email,
            phoneNumber = user.phoneNumber,
            identificationNumber = user.identificationNumber,
            isConfirmed = user.isConfirmed,
            role = user.role
        )
    }



}