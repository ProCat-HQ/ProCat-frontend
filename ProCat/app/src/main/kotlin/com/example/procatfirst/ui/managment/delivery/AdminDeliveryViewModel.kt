package com.example.procatfirst.ui.managment.delivery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.ClusterResult
import com.example.procatfirst.data.Delivery
import com.example.procatfirst.data.UserDataResponse
import com.example.procatfirst.repository.api.ApiCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AdminDeliveryViewModel : ViewModel()  {

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
                            deliveryPairs = result
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
                            deliveryPairs = result
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

    fun confirm() {
        for (deliveryManId in 0 until uiState.value.deliveryPairs.size) {
            for (deliveryId in 0 until uiState.value.deliveryPairs[deliveryManId].deliveries.size) {
                val b = uiState.value.deliveryPairs[deliveryManId].deliveries[deliveryId].deliveryId
                if (uiState.value.changedMap.containsKey(b)) {
                    Log.d("CLUSTER_MAN_ID", b.toString() + " " + uiState.value.changedMap[b].toString())

                    viewModelScope.launch {
                        val callback = {status: String ->
                            if(status == "SUCCESS") {
                                loadDeliveriesToSort()
                            }
                        }
                        if (uiState.value.changedMap[b] != null) {
                            ApiCalls.shared.changeDeliveryWithDeliverymanApi(b,
                                uiState.value.changedMap[b]!!, callback)
                        }
                    }
                } else {
                    Log.d("CLUSTER_MAN_ID", b.toString())
                    viewModelScope.launch {
                        val callback = {status: String ->
                            if(status == "SUCCESS") {
                                loadDeliveriesToSort()
                            }
                        }
                        ApiCalls.shared.changeDeliveryApi(b, callback)
                    }
                }
            }
        }
    }

    fun changeDeliveryman(deliveryId: Int, newDeliverymanId: Int) {
        val map = uiState.value.changedMap.toMutableMap()
        map[deliveryId] = newDeliverymanId
        _uiState.update { currentState ->
            currentState.copy(
                changedMap = map
            )
        }

    }

    fun getDeliverymanName(deliverymanId: Int): String {
        var name = ""
        viewModelScope.launch {
            val callback = {status: String, result: UserDataResponse ->
                if(status == "SUCCESS") {
                    name = result.fullName
                }
            }
            ApiCalls.shared.getUserDataApi(deliverymanId, callback)
        }
        return name
    }
}
