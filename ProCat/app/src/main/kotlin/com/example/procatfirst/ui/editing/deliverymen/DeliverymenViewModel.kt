package com.example.procatfirst.ui.editing.deliverymen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.ClusterResult
import com.example.procatfirst.data.Deliveryman
import com.example.procatfirst.data.User
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

    fun loadUsers() {
        viewModelScope.launch {
            val callback = {status: String, result: List<User> ->
                if(status == "SUCCESS") {

                    val courierNames = _uiState.value.deliverymen.map { it.fullName }.toSet()
                    val filteredUsers = result.filter { it.fullName !in courierNames }
                    _uiState.update { currentState ->
                        currentState.copy(
                            users = filteredUsers
                        )
                    }
                    /*
                    _uiState.update { currentState ->
                        currentState.copy(
                            users = result
                        )
                    } */
                }
            }
            ApiCalls.shared.getAllUsersApi(callback)
        }
    }

    fun makeCourier(user: User, carCapacity: String, workingHoursStart: String, workingHoursEnd: String, carId: String) {
        viewModelScope.launch {
            val callback = {status: String ->
                if(status == "SUCCESS") {
                    loadDeliverymen()
                    loadUsers()
                }
            }
            ApiCalls.shared.createDeliveryManFromUserApi(user.id, carCapacity, workingHoursStart, workingHoursEnd, carId, callback)
        }
    }

    fun deleteDeliveryman(id: Int) {
        viewModelScope.launch {
            val callback = {status: String ->
                if(status == "SUCCESS") {
                    loadDeliverymen()
                    loadUsers()
                }
            }
            ApiCalls.shared.deleteDeliverymanApi(id, callback)
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            ApiCalls.shared.deleteUserApi(id) {
                if(it == "SUCCESS") {
                    loadDeliverymen()
                    loadUsers()
                }
            }
        }
    }


}
