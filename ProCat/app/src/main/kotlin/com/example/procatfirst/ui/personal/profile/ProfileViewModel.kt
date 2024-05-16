package com.example.procatfirst.ui.personal.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.User
import com.example.procatfirst.data.UserDataProvider
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


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
            //ApiCalls.shared.
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
    fun fullSaveUserIdentificationNumber(password: String) {
        viewModelScope.launch {
            //ApiCalls.shared.
        }
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
    fun fullSaveUserEmail(password: String) {

        viewModelScope.launch {
            //ApiCalls.shared.
        }
    }


    fun checkPassword(password: String): Boolean {
        return password == "123456"
    }
    private fun getUserProfileInfo() {
        var trueUser : User? = null
        var user = UserDataProvider.users[0];
        var id = 1

        /*
        CoroutineScope(Dispatchers.IO).launch {
            trueUser = DataCoordinator.shared.getUserData()
            val user = UserDataProvider.users[0];
            _uiState.value = _uiState.value.copy(
                    userId = user.id,
                    //fullName = user.fullName,
                    fullName = trueUser!!.fullName,
                    email = trueUser!!.email,//user.email,
                    phoneNumber = user.phoneNumber,
                    identificationNumber = user.identificationNumber,
                    isConfirmed = user.isConfirmed,
                    role = trueUser!!.role//user.role
            )
            NotificationCoordinator.shared.sendIntent(SystemNotifications.myTestIntent)
        } */
        viewModelScope.launch {
            val callback = {status : String ->
                if (status == "SUCCESS") {
                    val temp = UserDataCache.shared.getUserData()
                    if (temp != null) {
                        user = temp
                    }
                } else {
                    //TODO Show error
                }
            }
            val idUser = UserDataCache.shared.getUserData()?.id
            if (idUser == null) {
                Log.i("UserId", "NULL")
                ApiCalls.shared.getUserDataApi(id, callback)
            } else {
                Log.i("UserId", "idUser")
                ApiCalls.shared.getUserDataApi(idUser, callback)
            }
        }

        _uiState.value = _uiState.value.copy(
                userId = user.id,
                fullName = user.fullName,
                email = user.email,
                phoneNumber = user.phoneNumber,
                identificationNumber = user.identificationNumber,
                isConfirmed = user.isConfirmed,
                role = user.role
        )
    }



}