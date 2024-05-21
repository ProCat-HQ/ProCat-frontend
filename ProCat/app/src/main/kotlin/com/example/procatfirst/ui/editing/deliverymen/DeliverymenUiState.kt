package com.example.procatfirst.ui.editing.deliverymen

import com.example.procatfirst.data.Deliveryman
import com.example.procatfirst.data.User


data class DeliverymenUiState (
    val deliverymen: List<Deliveryman> = emptyList(),
    val users: List<User> = emptyList()
)