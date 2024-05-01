package com.example.procatfirst.ui.cart

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
    private val _uiState = MutableStateFlow(ToolUiState())
    val uiState: StateFlow<ToolUiState> = _uiState.asStateFlow()

    init{
        open()
    }

    private fun open(){
        _uiState.value = ToolUiState()
    }

    fun updateTools() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    tools = DataCoordinator.shared.getUserCartPayload()
                )
            }
        }
    }

    fun changeIsActive(fi : Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isActive = fi
            )
        }
    }

}