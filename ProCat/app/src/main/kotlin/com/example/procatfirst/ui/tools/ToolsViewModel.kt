package com.example.procatfirst.ui.tools

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getCatalog
import com.example.procatfirst.repository.data_coordinator.loadCatalog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ToolsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState("", DataCoordinator.shared.getCatalog()))
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init{
        open()
    }

    var userInputSearch by mutableStateOf("")
        private set

    fun updateInputSearch(enteredWord: String){
        userInputSearch = enteredWord
    }

    fun search() {
        val searchQuery = userInputSearch.trim().lowercase()
        _uiState.update { currentState ->
            currentState.copy(
                tools = DataCoordinator.shared.getCatalog().filter { tool ->
                    tool.name.lowercase().contains(searchQuery) || tool.description.lowercase().contains(searchQuery)
                }
            )
        }
    }
    fun sortByPriceAscending() {
        _uiState.update { it.copy(tools = it.tools.sortedBy { tool -> tool.price }) }
    }

    fun sortByPriceDescending() {
        _uiState.update { it.copy(tools = it.tools.sortedByDescending { tool -> tool.price }) }
    }

    fun groupByCategory() {
    }

    fun resetFilters() {
        _uiState.update { currentState ->
            currentState.copy(
                tools = DataCoordinator.shared.getCatalog(),
                isActive = true,
            )
        }
    }

    fun updateTools() {
        if(DataCoordinator.shared.getCatalog().isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(
                    loadText = "Нет соединения с сервером"
                )
            }
        }
        else {
            _uiState.update { currentState ->
                currentState.copy(
                    tools = DataCoordinator.shared.getCatalog(),
                    isActive = true,
                )
            }
        }
    }

    private fun loadCatalog() {
        val callback = { NotificationCoordinator.shared.sendIntent(SystemNotifications.stuffAddedIntent) }
        viewModelScope.launch {
            DataCoordinator.shared.loadCatalog(callback)
        }
    }

    fun setIsActive(active : Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isActive = active
            )
        }
    }

    private fun open() {
        _uiState.value = SearchUiState("", DataCoordinator.shared.getCatalog(),
            DataCoordinator.shared.getCatalog().isNotEmpty()
        )
        loadCatalog()
    }

}