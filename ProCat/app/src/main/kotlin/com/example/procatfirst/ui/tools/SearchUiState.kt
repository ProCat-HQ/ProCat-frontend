package com.example.procatfirst.ui.tools

import com.example.procatfirst.data.Item2
import com.example.procatfirst.data.Tool

data class SearchUiState(
    val word: String = "",
    //val tools: List<Tool>,
    val tools: List<Item2>,
    val isActive: Boolean = false,
    val loadText: String = "Loading...",
)