package com.example.procatfirst.ui.courier.orders

import androidx.lifecycle.ViewModel
import com.example.procatfirst.data.Notification
import com.example.procatfirst.data.NotificationDataProvider
import com.example.procatfirst.data.Order
import com.example.procatfirst.data.OrderDataProvider
import kotlinx.coroutines.flow.MutableStateFlow

class CourierOrdersViewModel: ViewModel() {
    val orders: MutableStateFlow<List<Order>> = MutableStateFlow(
        OrderDataProvider.orders)

}