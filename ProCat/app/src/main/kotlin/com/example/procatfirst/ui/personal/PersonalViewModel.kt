package com.example.procatfirst.ui.personal

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

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

    fun confirmLogout(context: Context) {
        Log.d("Logout", "Conf")
        viewModelScope.launch {
            DataCoordinator.shared.logout(context)
            exitProcess(0)
        }

        _uiState.update { currentState ->
            currentState.copy(
                logout = false
            )
        }
        Log.d("Logout", "EXIT")

    }

}