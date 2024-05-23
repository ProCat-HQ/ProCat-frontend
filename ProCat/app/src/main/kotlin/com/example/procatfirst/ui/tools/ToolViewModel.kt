package com.example.procatfirst.ui.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Item2
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


class ToolViewModel(tool : Item2): ViewModel() {

    private val _uiState = MutableStateFlow(ToolUiState())
    val uiState: StateFlow<ToolUiState> = _uiState.asStateFlow()

    init{
        open(tool)
    }

    private fun open(tool : Item2) {
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