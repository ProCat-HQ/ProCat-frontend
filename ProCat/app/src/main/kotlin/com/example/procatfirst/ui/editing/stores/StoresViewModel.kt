package com.example.procatfirst.ui.editing.stores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Deliveryman
import com.example.procatfirst.data.Store
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.api.StoreApiCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StoresViewModel(): ViewModel() {

    private val _uiState = MutableStateFlow(StoresUiState())
    val uiState: StateFlow<StoresUiState> = _uiState.asStateFlow()

    init {
        loadStores()
    }

    private fun loadStores() {
        viewModelScope.launch {
            val callback = {status: String, result: List<Store> ->
                if(status == "SUCCESS") {
                    _uiState.update { currentState ->
                        currentState.copy(
                            stores = result
                        )
                    }
                }
            }
            StoreApiCalls.shared.getAllStoresApi(callback)
        }
    }

    fun editStore(store: Store) {
        viewModelScope.launch {
            val callback = {status: String ->
                if(status == "SUCCESS") {
                    loadStores()
                }
            }
            StoreApiCalls.shared.updateStoreApi(store.id, store.name, store.address, store.workingHoursStart, store.workingHoursEnd, callback)
        }
    }

    fun createStore(store: Store) {
        viewModelScope.launch {
            val callback = {status: String ->
                if(status == "SUCCESS") {
                    loadStores()
                }
            }
            StoreApiCalls.shared.createStoreApi(store.name, store.address, store.workingHoursStart, store.workingHoursEnd, callback)
        }
    }

}