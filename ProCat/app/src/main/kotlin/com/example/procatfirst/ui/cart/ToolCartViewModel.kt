package com.example.procatfirst.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import getUserCartPayload
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ToolCartViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(ToolsUiState())
    val uiState: StateFlow<ToolsUiState> = _uiState.asStateFlow()

    init{
        open()
    }

    private fun open(){
        //_uiState.value = ToolUiState()
        Log.d("CartOpen", "CartOpen")
        viewModelScope.launch {
            _uiState.value = ToolsUiState(true, DataCoordinator.shared.getUserCartPayload())
        }
    }

    fun updateTools() {
        viewModelScope.launch {
            changeIsActive(false)
            _uiState.update { currentState ->
                currentState.copy(
                    tools = DataCoordinator.shared.getUserCartPayload()
                )
            }
            changeIsActive(true)
        }
    }

    private fun changeIsActive(fi : Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isActive = fi
            )
        }
    }

}