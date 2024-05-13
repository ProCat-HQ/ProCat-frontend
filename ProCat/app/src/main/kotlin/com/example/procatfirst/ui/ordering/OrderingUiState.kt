package com.example.procatfirst.ui.ordering

import com.example.procatfirst.data.ToolWithCnt

data class OrderingUiState(
    val tools : List<ToolWithCnt> = emptyList(),
    val address: String = "",
    val selectedDate: String = "",
    val orderStatus: String = "",
    val selectedTimeFromHour: Int = 9,
    val selectedTimeFromMinute: Int = 0,
    val selectedTimeToHour: Int = 17,
    val selectedTimeToMinute: Int = 0
)