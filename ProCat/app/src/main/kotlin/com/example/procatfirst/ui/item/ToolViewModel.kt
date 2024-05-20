package com.example.procatfirst.ui.item

import addToolToUserCart
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToolViewModel(tool: Tool): ViewModel() {
    private val _uiState = MutableStateFlow(ToolUiState())
    val uiState: StateFlow<ToolUiState> = _uiState.asStateFlow()

    init{
        open(tool)
    }

    private fun open(tool: Tool) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                DataCoordinator.shared.getImage(tool.image) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            bitmap = it
                        )
                    }
                }
            }
        }
    }

    fun addToCart(tool : Tool) {
        val newAmount = _uiState.value.amount.plus(1)
        _uiState.update { currentState ->
            currentState.copy(
                addedToCart = true,
                amount = newAmount
            )
        }
        this.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                DataCoordinator.shared.addToolToUserCart(tool)
            }
        }

    }

}