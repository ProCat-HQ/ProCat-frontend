package com.example.procatfirst.ui.managment.delivery

import com.example.procatfirst.data.ClusterResult
import com.example.procatfirst.data.Delivery


data class AdminDeliveryUiState (
    val deliveries: List<ClusterResult> = emptyList(),
    val currentDelivery: Delivery? = null,
    val isDeliveryDialogOpen: Boolean = false
)