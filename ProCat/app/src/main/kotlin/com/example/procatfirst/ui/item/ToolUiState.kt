package com.example.procatfirst.ui.item

import android.graphics.Bitmap
import com.example.procatfirst.data.ItemFullPayload

data class ToolUiState(
    val addedToCart: Boolean = false,
    val amount: Int = 0,
    val bitmap: Bitmap? = null,
    val tool: ItemFullPayload? = null,
)
