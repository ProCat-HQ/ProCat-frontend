package com.example.procatfirst.ui.item

import android.graphics.Bitmap
import com.example.procatfirst.data.Tool

data class ToolUiState(
    val addedToCart: Boolean = false,
    val amount: Int = 0,
    val bitmap: Bitmap? = null,
    val tool: Tool = Tool(0, "Название", "Описание", 0, false, 0, "hammer.jpg")
)
