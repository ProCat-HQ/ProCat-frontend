package com.example.procatfirst.ui.tools

import com.example.procatfirst.data.Item2

data class SearchUiState(
    val word: String = "",
    val tools: List<Item2>,
    val isActive: Boolean = false,
    val loadText: String = "Loading...",
)