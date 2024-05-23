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
        /*
        viewModelScope.launch {
            val callback = {status: String, result: List<Item> ->
                if(status == "SUCCESS") {
                    _uiState.update { currentState ->
                        currentState.copy(
                            items = result
                        )
                    }
                }
            }
            ApiCalls.shared.getItemsApi(callback)
        } */
    }

    fun createItem() {
        /*
        viewModelScope.launch {
            val callback = { status: String ->
                if (status == "SUCCESS") {
                    loadItems()
                }
            }
            ApiCalls.shared.createItem(callback)
        } */
    }

    fun deleteItem() {

    }

    fun editItem() {

    }

}