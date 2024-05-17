package com.example.procatfirst.ui.managment.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.ClusterResult
import com.example.procatfirst.data.DeliveryLocation
import com.example.procatfirst.repository.api.ApiCalls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
INSERT INTO users (fullname, email, phone_number, identification_number, password_hash, role)
VALUES ('Админ Админович', 'admin@gmail.com', '+79998887766', '123', 'c9j1403c401h21c4923c40213hc4', 'admin'),
       ('Доставщик Доставщиков Доставщикович', 'deliveryman@gmail.com', '+71998887766', '234', 'c9j1403c401h21c4923c40213hc4', 'deliveryman'),
       ('User Usernamovich', 'user@gmail.com', '+72998887766', '345', 'c9j1403c401h21c4923c40213hc4', 'user');

 */

class AdminDeliveryViewModel(): ViewModel()  {

    private val _uiState = MutableStateFlow(AdminDeliveryUiState())
    val uiState: StateFlow<AdminDeliveryUiState> = _uiState.asStateFlow()

    init{
        val deliveryLocation = DeliveryLocation("Pirogova", "000", "111", 2)
        val deliveries: List<DeliveryLocation> = listOf(deliveryLocation, deliveryLocation)
        _uiState.update { currentState ->
            currentState.copy(
                clusterResult = listOf(ClusterResult(1, deliveries))
            )
        }
    }

    fun makeCluster() {
        viewModelScope.launch {
            val callback = {status: String, result: List<ClusterResult> ->
                if(status == "SUCCESS") {
                    _uiState.update { currentState ->
                        currentState.copy(
                            clusterResult = result
                        )
                    }
                }
            }
            ApiCalls.shared.makeCluster(callback)
        }
    }
}
