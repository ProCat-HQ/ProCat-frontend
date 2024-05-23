package com.example.procatfirst.ui.item

import addToolToUserCart
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Item2
import com.example.procatfirst.data.ItemFullPayload
import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getCurrentTool
import com.example.procatfirst.repository.data_coordinator.getImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToolViewModel(toolId: Int): ViewModel() {
    private val _uiState = MutableStateFlow(ToolUiState())
    val uiState: StateFlow<ToolUiState> = _uiState.asStateFlow()

    init{
        open(toolId)
    }

    private fun open(toolId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                DataCoordinator.shared.getCurrentTool(toolId) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            tool = it
                        )
                    }
                }
                uiState.value.tool?.images?.get(0)?.image?.let { loadImage(it) }
            }
        }
    }

    private suspend fun loadImage(name: String) {
        DataCoordinator.shared.getImage(name) {
            _uiState.update { currentState ->
                currentState.copy(
                    bitmap = it
                )
            }
        }
    }

    fun addToCart(tool : ItemFullPayload, context: Context) {
        val newAmount = _uiState.value.amount.plus(1)
        _uiState.update { currentState ->
            currentState.copy(
                addedToCart = true,
                amount = newAmount
            )
        }
        this.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var img = "hammer.jpg"
                //Fake
                if (tool.images != null && tool.images[0] != null) {
                    img = tool.images[0].image
                }
                DataCoordinator.shared.addToolToUserCart(Item2(tool.id, tool.name, tool.description, tool.price, tool.isInStock, tool.categoryId, tool.categoryName, img)) {
                    if (it != "") {
                        Toast.makeText(context, "Инструмента нет в наличии", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}