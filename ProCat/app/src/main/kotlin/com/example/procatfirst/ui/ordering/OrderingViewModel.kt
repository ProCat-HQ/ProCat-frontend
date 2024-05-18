package com.example.procatfirst.ui.ordering

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Order
import com.example.procatfirst.data.OrderRequest
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.createNewOrder
import getUserCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class OrderingViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(OrderingUiState())
    val uiState: StateFlow<OrderingUiState> = _uiState.asStateFlow()

    var address by mutableStateOf("")

    var delivery by mutableStateOf(true)

    var orderStatus by mutableStateOf("В ожидании подтверждения")

    init{
        open()
    }

    private fun open(){
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _uiState.value = OrderingUiState(tools = DataCoordinator.shared.getUserCart().values.toList())
            }
        }
    }

    fun updateSelectedPeriod(selectedPeriod: Int) {
        _uiState.value = _uiState.value.copy(periodInDays = selectedPeriod)
    }

    fun updateSelectedDate(selectedDate: String) {
        _uiState.value = _uiState.value.copy(selectedDate = selectedDate)
    }

    fun updateSelectedTimeFrom(selectedTimeFromHour: Int, selectedTimeFromMinute: Int) {
        _uiState.value = _uiState.value.copy(selectedTimeFromHour = selectedTimeFromHour, selectedTimeFromMinute = selectedTimeFromMinute)
    }

    fun updateSelectedTimeTo(selectedTimeToHour: Int, selectedTimeToMinute: Int) {
        _uiState.value = _uiState.value.copy(selectedTimeToHour = selectedTimeToHour, selectedTimeToMinute = selectedTimeToMinute)
    }

    private fun updateSelectedAddress(address: String) {
        _uiState.value = _uiState.value.copy(address = address)
    }

    fun order() {
        // send request to make order with date, time, address and items!
        updateSelectedAddress(address)
        //ApiCalls.shared.geocoderApi()
        viewModelScope.launch {
            val order = uiState.value
            val type : String = if (delivery) {
                "by car"
            } else {
                "by client"
            }
            var dateTimeEnd = LocalDate.parse(order.selectedDate)
            dateTimeEnd = dateTimeEnd.plusDays(order.periodInDays.toLong())
            Log.v("DATE", dateTimeEnd.toString())
            val deliveryTimeFrom = order.selectedDate + ' ' + order.selectedTimeFromHour.toString() + ':' + order.selectedTimeFromMinute.toString() + ":00"
            val deliveryTimeTo = dateTimeEnd.toString() + ' ' + order.selectedTimeToHour.toString() + ':' + order.selectedTimeToMinute.toString() + ":00"

            val newOrder = DataCoordinator.shared.createNewOrder(
                OrderRequest(deliveryTimeFrom,
                    "$dateTimeEnd 00:00:00", order.address, null, type, deliveryTimeFrom, deliveryTimeTo))
            if (newOrder == null) {
                //TODO ERROR MESSAGE
                Log.e("NEW ORDER", "It's null!")
            }
            else {
                //TODO show result
                Log.i("NEW ORDER", "It's OK")
            }
        }
    }


}