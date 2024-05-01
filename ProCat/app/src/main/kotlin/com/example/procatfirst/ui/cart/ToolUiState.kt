package com.example.procatfirst.ui.cart

import com.example.procatfirst.data.CartPayload

data class ToolUiState(
    val isActive: Boolean = true,
    val tools: CartPayload = CartPayload(setOf())
    )
