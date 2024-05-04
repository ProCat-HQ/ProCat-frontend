package com.example.procatfirst.ui.tools

import com.example.procatfirst.data.Tool

data class SearchUiState(
    val word: String = "",
    val tools: List<Tool>,
    val isActive: Boolean = false,
    val loadText: String = "Loading...",
)