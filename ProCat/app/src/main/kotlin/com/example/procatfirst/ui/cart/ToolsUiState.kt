package com.example.procatfirst.ui.cart

import com.example.procatfirst.data.CartPayload

data class ToolsUiState(
    val isActive: Boolean = true,
    val tools: CartPayload = CartPayload(setOf())//DataCoordinator.shared.getUserCartPayload()
    )
