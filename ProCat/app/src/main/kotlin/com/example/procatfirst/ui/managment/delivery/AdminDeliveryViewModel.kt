package com.example.procatfirst.ui.managment.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.ClusterResult
import com.example.procatfirst.data.Delivery
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
        loadDeliveriesToSort()
    }


    private fun loadDeliveriesToSort() {
        viewModelScope.launch {
            val callback = {status: String, result: List<ClusterResult> ->
                if(status == "SUCCESS") {
                    _uiState.update { currentState ->
                        currentState.copy(
                            deliveries = result
                        )
                    }
                }
            }
            ApiCalls.shared.getAllDeliveriesToSortApi(callback)
        }
    }

    fun makeCluster() {
        viewModelScope.launch {
            val callback = {status: String, result: List<ClusterResult> ->
                if(status == "SUCCESS") {
                    _uiState.update { currentState ->
                        currentState.copy(
                            deliveries = result
                        )
                    }
                }
            }
            ApiCalls.shared.makeCluster(callback)
        }
    }

    fun getDelivery(id: Int) {
        viewModelScope.launch {
            val callback = {status: String, result: Delivery ->
                if(status == "SUCCESS") {
                    _uiState.update { currentState ->
                        currentState.copy(
                            currentDelivery = result,
                            isDeliveryDialogOpen = true
                        )
                    }
                }
            }
            ApiCalls.shared.getDeliveryFromDeliveryIdApi(id, callback)
        }
    }

    fun closeDeliveryDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                isDeliveryDialogOpen = false
            )
        }
    }
}
