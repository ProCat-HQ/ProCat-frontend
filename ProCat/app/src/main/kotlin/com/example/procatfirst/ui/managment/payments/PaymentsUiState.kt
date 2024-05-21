package com.example.procatfirst.ui.managment.payments

import com.example.procatfirst.data.Payment


data class PaymentsUiState (
    var payments: List<Payment> = emptyList()
)