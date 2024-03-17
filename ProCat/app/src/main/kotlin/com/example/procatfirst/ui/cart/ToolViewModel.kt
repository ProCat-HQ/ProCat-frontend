package com.example.procatfirst.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Tool
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToolViewModel: ViewModel()  {
    private val _uiState = MutableStateFlow(ToolUiState())
    val uiState: StateFlow<ToolUiState> = _uiState.asStateFlow()


    var quantity by mutableStateOf(1)
        private set

    init{
        open()
    }

    fun removeFromCart(tool : Tool) {
        _uiState.update { currentState ->
            currentState.copy(
            )
        }
        this.viewModelScope.launch {
            withContext(Dispatchers.Default) {
                DataCoordinator.shared.removeToolFromUserCart(tool)
            }
            NotificationCoordinator.shared.sendIntent(SystemNotifications.delInCartIntent)
        }
    }

    fun increaseAmount() {
        quantity++
    }

    fun decreaseAmount() {
        if (quantity > 1) {
            quantity--
        }
    }

    private fun open(){
        _uiState.value = ToolUiState()
    }

}