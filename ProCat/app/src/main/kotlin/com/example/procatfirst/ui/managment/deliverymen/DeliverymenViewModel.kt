package com.example.procatfirst.ui.managment.deliverymen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.ClusterResult
import com.example.procatfirst.data.Deliveryman
import com.example.procatfirst.repository.api.ApiCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DeliverymenViewModel(): ViewModel()  {

    private val _uiState = MutableStateFlow(DeliverymenUiState())
    val uiState: StateFlow<DeliverymenUiState> = _uiState.asStateFlow()

    init{
        loadDeliverymen()

    }

    private fun loadDeliverymen() {
        viewModelScope.launch {
            val callback = {status: String, result: List<Deliveryman> ->
                if(status == "SUCCESS") {
                    _uiState.update { currentState ->
                        currentState.copy(
                            deliverymen = result
                        )
                    }
                }
            }
            ApiCalls.shared.getAllDeliverymenApi(callback)
        }
    }


}
