package com.example.procatfirst.ui.personal

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.ui.registration.RegistrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonalViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(PersonalUiState())
    val uiState: StateFlow<PersonalUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = PersonalUiState()
    }


    fun openLogoutDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                logout = true
            )
        }
    }

    fun cancelLogout() {
        Log.d("logout", "F")

        _uiState.update { currentState ->
            currentState.copy(
                logout = false
            )
        }
    }

    fun confirmLogout() {
        Log.d("logout", "Conf")
        viewModelScope.launch {
            DataCoordinator.shared.logout()
        }

        _uiState.update { currentState ->
            currentState.copy(
                logout = false
            )
        }
        Log.d("logout", "EXIT")
    }

}