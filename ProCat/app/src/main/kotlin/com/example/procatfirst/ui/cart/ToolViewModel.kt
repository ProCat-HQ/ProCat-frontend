package com.example.procatfirst.ui.cart

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.R
import com.example.procatfirst.data.CartItem
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getImage
import decreaseToolCount
import increaseToolCount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import removeToolFromUserCart

class ToolViewModel(tool : CartItem): ViewModel()  {
    private val _uiState = MutableStateFlow(ToolUiState(tool))
    val uiState: StateFlow<ToolUiState> = _uiState.asStateFlow()


    init{
        open(tool)
    }

    fun removeFromCart(tool : CartItem) {
        _uiState.update { currentState ->
            currentState.copy(
            )
        }
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                DataCoordinator.shared.removeToolFromUserCart(tool.id)
            }
            NotificationCoordinator.shared.sendIntent(SystemNotifications.delInCartIntent)
        }
    }

    fun increaseAmount(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                DataCoordinator.shared.increaseToolCount(uiState.value.tool.id) {
                    if (it != "") {
                        Toast.makeText(context, R.string.tool_is_not_available, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            NotificationCoordinator.shared.sendIntent(SystemNotifications.delInCartIntent)
        }
    }

    fun decreaseAmount() {

        if (uiState.value.tool.count > 1) {
            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    DataCoordinator.shared.decreaseToolCount(uiState.value.tool.id)
                }
                NotificationCoordinator.shared.sendIntent(SystemNotifications.delInCartIntent)
            }
        }

    }

    private fun open(tool: CartItem){
        _uiState.value = ToolUiState(tool)

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

}