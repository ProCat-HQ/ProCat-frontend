package com.example.procatfirst.ui.personal.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getUserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    var action: (String) -> Unit = {}

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

    fun fullSaveUserPhoneNumber(password: String) {
        if (checkPassword(password)) {
            viewModelScope.launch {
                //ApiCalls.shared.
            }
        }

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

    fun fullSaveUserFullName(password: String) {
        viewModelScope.launch {
            ApiCalls.shared.changeName(userInputFullName, password) {
                if (it == "SUCCESS") {
                    getUserProfileInfo()
                }
                else {
                    errorDialog(it)
                }
            }
        }
    }

    fun showPasswordDialog(act: (String) -> Unit) {
        _uiState.update { currentState ->
            currentState.copy(passwordDialog = true)
        }
        action = act
    }

    fun hidePasswordDialog() {
        _uiState.update { currentState ->
            currentState.copy(passwordDialog = false)
        }
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

    fun fullSaveUserIdentificationNumber(password: String, context: Context) {
        viewModelScope.launch {
            ApiCalls.shared.changeIin(userInputIdentificationNumber, password) {
                if (it == "SUCCESS") {
                    Log.d("IIN", "$userInputIdentificationNumber $password")
                    getUserProfileInfo()
                    Toast.makeText(context, "Успех", Toast.LENGTH_SHORT).show()
                }
                else {
                    errorDialog("Ваш ИИН некорректен или не соответствует указанному ФИО $it")
                }
            }
        }
    }

    //Email
    var userInputEmail by mutableStateOf(_uiState.value.email)
        private set

    fun updateUserEmail(enteredEmail: String){
        userInputEmail = enteredEmail
    }

    private fun errorDialog(message: String) {
        _uiState.update { currentState ->
            currentState.copy(errorDialog = true, errorMessage = message)
        }
    }

    fun hideErrorDialog() {
        _uiState.update { currentState ->
            currentState.copy(errorDialog = false)
        }
    }

    fun saveUserEmail() {
        _uiState.update { currentState ->
            currentState.copy(email = userInputEmail)
        }
        updateUserEmail(userInputEmail)
    }
    fun fullSaveUserEmail(password: String) {
        viewModelScope.launch {
            ApiCalls.shared.changeEmail(userInputEmail, password) {
                if (it == "SUCCESS") {
                    getUserProfileInfo()
                }
                else {
                    errorDialog("Некорректный email или пароль $it")
                }
            }
        }
    }


    private fun checkPassword(password: String): Boolean {
        return password == "123456"
    }
    private fun getUserProfileInfo() {
        viewModelScope.launch {

            val idUser = UserDataCache.shared.getUserData()?.id
            if (idUser != null) {
                Log.i("UserId", idUser.toString())

                DataCoordinator.shared.getUserData() {
                    _uiState.value = _uiState.value.copy(
                        userId = it.id,
                        fullName = it.fullName,
                        email = it.email,
                        phoneNumber = it.phoneNumber,
                        identificationNumber = it.identificationNumber,
                        isConfirmed = it.isConfirmed,
                        role = it.role
                    )
                }
            }
        }

    }



}