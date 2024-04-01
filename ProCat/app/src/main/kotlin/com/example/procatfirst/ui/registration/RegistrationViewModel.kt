package com.example.procatfirst.ui.registration

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

class RegistrationViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    init{
        open()
    }


    var userInputPhoneNumber by mutableStateOf("")
        private set

    var userInputFatherName by mutableStateOf("")
        private set

    var userInputLastName by mutableStateOf("")
        private set

    var userInputPassword by mutableStateOf("")
        private set

    var userInputFirstName by mutableStateOf("")
        private set



    fun check() {
        checkUserPassword()
        checkUserPhoneNumber()
        checkUserLastName()
        checkUserFirstName()
        checkUserFatherName()

    }

    fun updateUserLastName(enteredLastName: String){
        userInputLastName = enteredLastName
    }

    fun updateUserFirstName(enteredFirstName: String){
        userInputFirstName = enteredFirstName
    }
    fun updateUserFatherName(enteredFatherName: String){
        userInputFatherName = enteredFatherName
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
        updateUserPhoneNumber("")
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

    private fun checkUserLastName() {
        if (userInputLastName.equals("LastName", ignoreCase = true)) {
            NotificationCoordinator.shared.sendIntent(SystemNotifications.loginIntent)
            _uiState.update { currentState ->
                currentState.copy(
                    enteredLastNameWrong = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredLastNameWrong = true)
            }
        }
        updateUserLastName("")
    }

    private fun checkUserFirstName() {
        if (userInputFirstName.equals("FirstName", ignoreCase = true)) {
            NotificationCoordinator.shared.sendIntent(SystemNotifications.loginIntent)
            _uiState.update { currentState ->
                currentState.copy(
                    enteredFirstNameWrong = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredFirstNameWrong = true)
            }
        }
        updateUserFirstName("")
    }

    private fun checkUserFatherName() {
        if (userInputFatherName.equals("FatherName", ignoreCase = true)) {
            NotificationCoordinator.shared.sendIntent(SystemNotifications.loginIntent)
            _uiState.update { currentState ->
                currentState.copy(
                    enteredFatherNameWrong = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredFatherNameWrong = true)
            }
        }
        updateUserFatherName("")
    }

    fun forgotPassword() {

    }

    private fun open(){
        _uiState.value = RegistrationUiState()
    }

}