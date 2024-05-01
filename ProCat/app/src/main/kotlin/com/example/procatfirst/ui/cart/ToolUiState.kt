package com.example.procatfirst.ui.cart

import com.example.procatfirst.data.CartPayload
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import getUserCartPayload

data class ToolUiState(
    val isActive: Boolean = true,
    val tools: CartPayload = DataCoordinator.shared.getUserCartPayload()
    )
