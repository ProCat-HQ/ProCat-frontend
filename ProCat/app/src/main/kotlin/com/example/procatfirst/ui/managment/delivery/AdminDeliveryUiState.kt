package com.example.procatfirst.ui.managment.delivery

import com.example.procatfirst.data.ClusterResult


data class AdminDeliveryUiState (
    val deliveries: List<ClusterResult> = emptyList()
)