package com.example.procatfirst.ui.registration

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.api.ApiCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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



    fun checkUserPhoneNumber() {
        if (userInputPhoneNumber.length < 11) {

            _uiState.update { currentState ->
                currentState.copy(enteredPhoneNumberWrong = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredPhoneNumberWrong = false)
            }
        }

    }

     fun checkUserPassword() {
        if (userInputPassword.length < 3) {
            _uiState.update { currentState ->
                currentState.copy(
                    enteredPasswordWrong = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredPasswordWrong = false)
            }
        }
    }

    fun checkUserLastName() {
        if (userInputLastName.length < 2) {
            _uiState.update { currentState ->
                currentState.copy(
                    enteredLastNameWrong = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredLastNameWrong = false)
            }
        }
    }

    fun checkUserFirstName() {
        if (userInputFirstName.length < 2) {
            _uiState.update { currentState ->
                currentState.copy(
                    enteredFirstNameWrong = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredFirstNameWrong = false)
            }
        }
    }

    fun checkUserFatherName() {
        _uiState.update { currentState ->
            currentState.copy(
                enteredFatherNameWrong = false
            )
        }
    }


    var isInputCodeDialogVisible by mutableStateOf(false)
        private set

    var userInputCode by mutableStateOf("")
        private set

    fun updateUserCode(enteredCode: String){
        userInputCode = enteredCode
    }

    fun checkCode() {
        //checkCode
    }
    fun closeDialog() {
        isInputCodeDialogVisible = false
    }
    fun sendCodeAgain() {

    }

    fun signUp(onAuth: () -> Unit, context: Context) {
        check()

        if (!_uiState.value.enteredLastNameWrong && !_uiState.value.enteredPhoneNumberWrong
            && !_uiState.value.enteredFirstNameWrong && !_uiState.value.enteredPasswordWrong) {
            val fullName: String = "$userInputFirstName $userInputLastName $userInputFatherName"
            viewModelScope.launch {
                ApiCalls.shared.signUpApi(userInputPhoneNumber, userInputPassword, fullName) {
                    if (it == "SUCCESS") {
                        Toast.makeText(context, "Успешная регистрация", Toast.LENGTH_SHORT).show()
                        onAuth()
                    }
                    else {
                        Toast.makeText(context, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //createNewVerificationCode
            isInputCodeDialogVisible = true
        }
    }

    private fun open(){
        _uiState.value = RegistrationUiState()
    }




}