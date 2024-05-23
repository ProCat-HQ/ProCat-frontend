package com.example.procatfirst.ui.cart

import android.graphics.Bitmap
import com.example.procatfirst.data.CartItem

data class ToolUiState(
    val tool: CartItem,
    val bitmap: Bitmap? = null,
)