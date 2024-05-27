package com.example.procatfirst.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getUserData
import getUserCart
import getUserCartPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = CartUiState()
        open()
    }

    private fun open() {
        viewModelScope.launch { withContext(Dispatchers.IO) {
            DataCoordinator.shared.getUserCart()

        } }
    }

    fun closeDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                confirmIinDialog = false,
                emptyDialog = false,
            )
        }
    }

    private fun showIinDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                confirmIinDialog = true,
                emptyDialog = false,
            )
        }
    }

    private fun showEmptyDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                confirmIinDialog = false,
                emptyDialog = true,
            )
        }
    }

    fun checkIsEmpty(ordering: () -> Unit) {
        viewModelScope.launch {
            if (DataCoordinator.shared.getUserCartPayload().items.isEmpty()) {
                showEmptyDialog()
            } else {
                DataCoordinator.shared.getUserData() {
                    if (it.identificationNumber != "") {
                        ordering()
                    } else {
                        showIinDialog()
                    }
                }
            }
        }
    }
}
