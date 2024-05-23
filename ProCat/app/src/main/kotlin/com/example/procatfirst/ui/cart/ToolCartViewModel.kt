package com.example.procatfirst.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import getUserCart
import getUserCartPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToolCartViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(ToolsUiState())
    val uiState: StateFlow<ToolsUiState> = _uiState.asStateFlow()

    init{
        open()
    }

    private fun open(){
        Log.d("CartOpen", "CartOpen")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                DataCoordinator.shared.getUserCart()
                _uiState.value = ToolsUiState(true, DataCoordinator.shared.getUserCartPayload())
            }
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