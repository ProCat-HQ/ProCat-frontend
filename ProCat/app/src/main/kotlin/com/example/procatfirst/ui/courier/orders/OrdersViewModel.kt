package com.example.procatfirst.ui.courier.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Delivery
import com.example.procatfirst.data.DeliveryOrder
import com.example.procatfirst.data.Deliveryman
import com.example.procatfirst.data.Notification
import com.example.procatfirst.data.NotificationDataProvider
import com.example.procatfirst.data.Order
import com.example.procatfirst.data.OrderDataProvider
import com.example.procatfirst.data.Point
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getAllOrders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourierOrdersViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(CourierOrdersUiState())
    val uiState: StateFlow<CourierOrdersUiState> = _uiState.asStateFlow()

    init{
        loadOrders()
        //loadFakeData()
    }

    private fun loadFakeData() {
        val fakeDelivery = Delivery(1, "20:40", "20:50", "car", DeliveryOrder(10, "ready", 1000, "Pirogova", "---", "+++"))
        val fakeDeliveries = listOf(fakeDelivery)
        _uiState.update { currentState ->
            currentState.copy(
                deliveries = fakeDeliveries
            )
        }
        _uiState.update { currentState ->
            currentState.copy(
                orderStatus = fakeDeliveries[0].order.status
            )
        }
    }

    private fun loadOrders() {
        val userId = UserDataCache.shared.getUserData()?.id

        viewModelScope.launch {
            val callback = {status: String, result: List<Delivery>? ->
                if(status == "SUCCESS" && result != null) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            deliveries = result
                        )
                    }
                }
            }
            if (userId != null) {
                ApiCalls.shared.getDeliveriesForDeliverymanApi(userId, callback)
            }
        }
    }

    fun changeOrderStatus(orderId: Int, orderStatus: String) {
        _uiState.update { currentState ->
            currentState.copy(
                orderStatus = orderStatus
            )
        }
        /*
        viewModelScope.launch {
            val callback = {status: String ->
                if(status == "SUCCESS") {
                    _uiState.update { currentState ->
                        currentState.copy(
                            orderStatus = orderStatus
                        )
                    }
                }
            }
            ApiCalls.shared.(orderId, orderStatus, callback)
        } */

    }


    //val orders: MutableStateFlow<List<Order>> = MutableStateFlow(
    //       DataCoordinator.shared.getAllOrders())

    fun createRoute() {
        viewModelScope.launch {
            val callback = {status: String, result: List<Point> ->
                if(status == "SUCCESS" && result != null) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            points = result
                        )
                    }
                }
            }
            ApiCalls.shared.createRoute(callback)
        }
    }


}