package com.example.procatfirst.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init{
        open()
    }



    var userInputPassword by mutableStateOf("")
        private set

    var userInputPhoneNumber by mutableStateOf("")
        private set



    fun check() {
        checkUserPassword()
        checkUserPhoneNumber()
    }

    fun updateUserPassword(enteredPassword: String){
        userInputPassword = enteredPassword
    }

    fun updateUserPhoneNumber(enteredPhoneNumber: String){
        userInputPhoneNumber = enteredPhoneNumber
    }



    private fun checkUserPhoneNumber() {
        if (userInputPhoneNumber == "1111") {
            //val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            NotificationCoordinator.shared.sendIntent(SystemNotifications.loginIntent)
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredPhoneNumberWrong = true)
            }
        }
        updateUserPassword("")
    }

    private fun checkUserPassword() {
        if (userInputPassword.equals("1111", ignoreCase = true)) {
            NotificationCoordinator.shared.sendIntent(SystemNotifications.loginIntent)
            _uiState.update { currentState ->
                currentState.copy(
                    enteredPasswordWrong = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredPasswordWrong = true)
            }
        }
        updateUserPassword("")
    }

    fun forgotPassword() {

    }

    private fun open(){
        _uiState.value = AuthUiState()
    }

}