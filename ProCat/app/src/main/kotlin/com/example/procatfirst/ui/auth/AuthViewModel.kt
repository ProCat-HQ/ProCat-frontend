package com.example.procatfirst.ui.auth

//import com.example.procatfirst.ProCatApplication
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.User
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.setUserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    //private val userRoleRepository: UserRoleRepository
): ViewModel()  {


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



    private fun checkUserPhoneNumber(): Boolean {
        if //(userInputPhoneNumber.length == 11) {
            (userInputPhoneNumber != "") {
            //val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            CoroutineScope(Dispatchers.IO).launch {//TODO не на том этапе пишем данные юзера - они ещё не полные.
                DataCoordinator.shared.setUserData(User(1, userInputPhoneNumber,
                    "$userInputPhoneNumber@mail.ru", "", "", false, userInputPhoneNumber, "", ""))
            }

            //NotificationCoordinator.shared.sendIntent(SystemNotifications.loginIntent)
            return true
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredPhoneNumberWrong = true)
            }
        }
        updateUserPhoneNumber("")
        return false
    }

    fun wrongPassword() {
        _uiState.update { currentState ->
            currentState.copy(
                enteredPasswordWrong = true
            )
        }
    }

    private fun checkUserPassword(): Boolean {
        if (userInputPassword.length > 2) {
            //NotificationCoordinator.shared.sendIntent(SystemNotifications.loginIntent)
            _uiState.update { currentState ->
                currentState.copy(
                    enteredPasswordWrong = false
                )
            }
            //updateUserPassword("")
            return true
        }
        _uiState.update { currentState ->
            currentState.copy(enteredPasswordWrong = true)
        }

        updateUserPassword("")
        return false
    }

    fun signIn() {
         if (checkUserPassword() && checkUserPhoneNumber()) {
            viewModelScope.launch {
                val response = ApiCalls.shared.signInApi(userInputPhoneNumber, userInputPassword)
                if (response) {
                    NotificationCoordinator.shared.sendIntent(SystemNotifications.loginIntent, "Response","SUCCESS")
                }
                else {
                    NotificationCoordinator.shared.sendIntent(SystemNotifications.loginIntent, "Response", "FAIL")
                }
            }
        }
    }

    fun forgotPassword() {

    }


    /*
    fun selectRole(userRole: String) {
        viewModelScope.launch {
            userRoleRepository.saveUserRole(userRole)
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ProCatApplication)
                AuthViewModel(application.userRoleRepository)
            }
        }
    } */



    private fun open(){
        _uiState.value = AuthUiState()
    }

}