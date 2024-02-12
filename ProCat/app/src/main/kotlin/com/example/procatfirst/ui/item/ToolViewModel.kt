package com.example.procatfirst.ui.item

import androidx.lifecycle.ViewModel
import com.example.procatfirst.R
import com.example.procatfirst.data.Tool
import com.example.procatfirst.data.ToolDataProvider
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import com.example.procatfirst.repository.data_storage_deprecated.updateAddToolsInCart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ToolViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ToolUiState())
    val uiState: StateFlow<ToolUiState> = _uiState.asStateFlow()

    init{
        openFirstTool()
    }

    private fun openFirstTool() {
        val firstTool = ToolDataProvider.tools.firstOrNull()
        firstTool?.let {
            _uiState.value = ToolUiState(tool = it)
        }
    }

    fun addToCart() {
        val newAmount = _uiState.value.amount.plus(1)
        _uiState.update { currentState ->
            currentState.copy(
                addedToCart = true,
                amount = newAmount
            )
        }
        val extraTool = Tool(22, "Набор", R.drawable.set, "desc", "extras", 2000)
        //DataCoordinatorOLD.shared.updateAddToolsInCart(_uiState.value.tool)
        DataCoordinator.shared.addToolToUserCart(_uiState.value.tool)
        DataCoordinator.shared.addToolToUserCart(extraTool)

    }

    fun removeFromCart() {
        _uiState.update { currentState ->
            currentState.copy(
                addedToCart = false,
                amount = 0
            )
        }
        DataCoordinator.shared.removeToolFromUserCart(_uiState.value.tool)
    }
}