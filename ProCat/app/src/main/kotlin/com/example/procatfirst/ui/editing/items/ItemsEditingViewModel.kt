package com.example.procatfirst.ui.editing.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Deliveryman
import com.example.procatfirst.data.Item
import com.example.procatfirst.repository.api.ApiCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemsEditingViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(ItemsEditingUiState())
    val uiState: StateFlow<ItemsEditingUiState> = _uiState.asStateFlow()

    init{
        loadItems()
    }

    private fun loadItems() {
    }

    fun createItem() {
    }

    fun deleteItem() {

    }

    fun editItem() {

    }

}