package com.example.procatfirst.ui.courier.orders

import com.example.procatfirst.data.Delivery

data class CourierOrdersUiState (
    val deliveries: List<Delivery> = emptyList(),
    val orderStatus: String = ""
)