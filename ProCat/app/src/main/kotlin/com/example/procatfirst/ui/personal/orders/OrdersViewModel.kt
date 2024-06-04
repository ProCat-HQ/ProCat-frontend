package com.example.procatfirst.ui.personal.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Order
import com.example.procatfirst.data.OrderDataProvider
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getUserOrders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(OrdersUiState(listOf()))
    val uiState: StateFlow<OrdersUiState> = _uiState.asStateFlow()

    init {
        open()
    }

    val deliveryOrders: MutableStateFlow<List<Order>> = MutableStateFlow(
        OrderDataProvider.deliveryOrders)

    private fun open() {
        viewModelScope.launch {
            DataCoordinator.shared.getUserOrders {
                _uiState.value = OrdersUiState(it)
            }
        }
    }

    fun cancelOrder(id: Int) {
        viewModelScope.launch {
            val callback = {status: String ->
                if(status == "SUCCESS") {
                    open()
                }
            }
            ApiCalls.shared.cancelOrderApi(id, callback)
        }
    }

}