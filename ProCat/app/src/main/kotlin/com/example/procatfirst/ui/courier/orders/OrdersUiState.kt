package com.example.procatfirst.ui.courier.orders

import com.example.procatfirst.data.Delivery
import com.example.procatfirst.data.Point

data class CourierOrdersUiState (
    val deliveries: List<Delivery> = emptyList(),
    val orderStatus: String = "",
    val points: List<Point> = emptyList(),
    val mapButtonVisible: Boolean = false,
)