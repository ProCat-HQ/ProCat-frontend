package com.example.procatfirst.ui.ordering

import com.example.procatfirst.data.CartItem

data class OrderingUiState(
    val tools : List<CartItem> = emptyList(),
    val address: String = "",
    val selectedDate: String = "",
    val orderStatus: String = "",
    val selectedTimeFromHour: Int = 9,
    val selectedTimeFromMinute: Int = 0,
    val selectedTimeToHour: Int = 17,
    val selectedTimeToMinute: Int = 0
)