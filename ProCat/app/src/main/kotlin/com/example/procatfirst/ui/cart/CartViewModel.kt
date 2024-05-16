package com.example.procatfirst.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import getUserCartPayload
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel: ViewModel()  {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    var checked by mutableStateOf(false)
        private set

    init {
        _uiState.value = CartUiState()
        checked = false
    }

    fun closeDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                orderDialog = false,
                emptyDialog = false,
            )
        }
    }

    private fun showOrderDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                orderDialog = true,
                emptyDialog = false,
            )
        }
    }

    private fun showEmptyDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                orderDialog = false,
                emptyDialog = true,
            )
        }
    }

    fun checkIsEmpty() {
        viewModelScope.launch {
            if (DataCoordinator.shared.getUserCartPayload().items.isEmpty()) {
                showEmptyDialog()
            }
            else {
                showOrderDialog()
            }
        }

    }
}
