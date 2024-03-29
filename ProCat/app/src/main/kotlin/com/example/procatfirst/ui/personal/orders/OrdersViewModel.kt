package com.example.procatfirst.ui.personal.orders

import androidx.lifecycle.ViewModel
import com.example.procatfirst.data.Order
import com.example.procatfirst.data.OrderDataProvider
import kotlinx.coroutines.flow.MutableStateFlow

class OrdersViewModel: ViewModel() {
    val orders: MutableStateFlow<List<Order>> = MutableStateFlow(
        OrderDataProvider.orders)
    val deliveryOrders: MutableStateFlow<List<Order>> = MutableStateFlow(
        OrderDataProvider.deliveryOrders)

}