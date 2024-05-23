package com.example.procatfirst.ui.managment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Order
import com.example.procatfirst.data.OrderFull
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getAllOrders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrdersManagerViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(OrdersManagerUiState(listOf()))
    val uiState: StateFlow<OrdersManagerUiState> = _uiState.asStateFlow()

    init {
        getAllOrders()
    }

    private fun getAllOrders() {
        viewModelScope.launch {
            val callback = {status: String, result: List<OrderFull>? ->
                if(status == "SUCCESS" && result != null) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            orders = result,
                        )
                    }
                }
            }
            ApiCalls.shared.getAllOrdersApi(callback)
        }
    }

    fun changeOrderStatus(id: Int, newStatus: String) {
        viewModelScope.launch {
            val callback = {status: String,->
                if(status == "SUCCESS") {
                    getAllOrders()
                }
            }
            ApiCalls.shared.changeOrderStatusApi(id, newStatus, callback)
        }
    }

}