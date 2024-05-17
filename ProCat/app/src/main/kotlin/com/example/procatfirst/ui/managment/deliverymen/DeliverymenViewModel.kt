package com.example.procatfirst.ui.managment.deliverymen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class DeliverymenViewModel(): ViewModel()  {

    private val _uiState = MutableStateFlow(DeliverymenUiState())
    val uiState: StateFlow<DeliverymenUiState> = _uiState.asStateFlow()

    init{

    }


}
