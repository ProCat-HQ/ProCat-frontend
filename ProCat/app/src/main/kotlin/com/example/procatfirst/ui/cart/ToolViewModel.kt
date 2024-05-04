package com.example.procatfirst.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.CartItem
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
import removeToolFromUserCart

class ToolViewModel(tool : CartItem): ViewModel()  {
    private val _uiState = MutableStateFlow(ToolUiState(tool.count))
    val uiState: StateFlow<ToolUiState> = _uiState.asStateFlow()

//    var quantity by mutableStateOf(1)
//        private set

    init{
        open(tool)
    }

    fun removeFromCart(tool : CartItem) {
        _uiState.update { currentState ->
            currentState.copy(
            )
        }
        this.viewModelScope.launch {
            withContext(Dispatchers.Default) {
                DataCoordinator.shared.removeToolFromUserCart(tool.id)
            }
            NotificationCoordinator.shared.sendIntent(SystemNotifications.delInCartIntent)
        }
    }

    fun increaseAmount() {
        //quantity++
        _uiState.update { currentState ->
            currentState.copy(
                amount = uiState.value.amount + 1
            )
        }
    }

    fun decreaseAmount() {
//        if (quantity > 1) {
//            quantity--
//        }
        if (uiState.value.amount > 1) {
            _uiState.update { currentState ->
                currentState.copy(
                    amount = uiState.value.amount - 1
                )
            }
        }

    }

    private fun open(tool: CartItem){
        _uiState.value = ToolUiState(tool.count)
    }

}