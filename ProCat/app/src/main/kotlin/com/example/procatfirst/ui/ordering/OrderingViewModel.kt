package com.example.procatfirst.ui.ordering

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.OrderRequest
import com.example.procatfirst.data.Store
import com.example.procatfirst.data.User
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.createNewOrder
import getUserCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class OrderingViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(OrderingUiState())
    val uiState: StateFlow<OrderingUiState> = _uiState.asStateFlow()

    var country by mutableStateOf("")
    var city by mutableStateOf("")
    var address by mutableStateOf("")

    var delivery by mutableStateOf(true)

    var orderStatus by mutableStateOf("В ожидании подтверждения")

    init{
        open()
        getStoresAddresses()
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
        //_uiState.value = _uiState.value.copy(address = address)
        _uiState.value = _uiState.value.copy(address = "$country, $city, $address")

    }

    fun order(context: Context, nextPage: (OrderingViewModel) -> Unit) {
        // send request to make order with date, time, address and items!
        updateSelectedAddress(address)
        viewModelScope.launch {
            val order = uiState.value
            val type : String = if (delivery) {
                "by car"
            } else {
                "by client"
            }
            val timeFromHour = if (order.selectedTimeFromHour == 0) { "00" }
            else { order.selectedTimeFromHour.toString() }
            val timeToHour = if (order.selectedTimeToHour == 0) { "00" }
            else { order.selectedTimeToHour.toString() }
            val timeFromMinutes = if (order.selectedTimeFromMinute == 0) { "00" }
            else { order.selectedTimeFromMinute.toString() }
            val timeToMinutes = if (order.selectedTimeToMinute == 0) { "00" }
            else { order.selectedTimeToMinute.toString() }
            var dateEnd = LocalDate.parse(order.selectedDate)
            dateEnd = dateEnd.plusDays(order.periodInDays.toLong())
            Log.v("DATE", dateEnd.toString())
            val deliveryTimeFrom = order.selectedDate + ' ' + timeFromHour + ':' + timeFromMinutes + ":00"
            val deliveryTimeTo = "$dateEnd $timeToHour:$timeToMinutes:00"

            val newOrder = DataCoordinator.shared.createNewOrder(
                OrderRequest(deliveryTimeFrom,
                    "$dateEnd 00:00:00", order.address, null, type, deliveryTimeFrom, deliveryTimeTo))
            if (newOrder == null) {
                //TODO ERROR MESSAGE
                Toast.makeText(context, "Ошибка при оформлении заказа", Toast.LENGTH_SHORT).show()
                Log.e("NEW ORDER", "It's null!")
            }
            else {
                //TODO show result
                nextPage(this@OrderingViewModel)
                Log.i("NEW ORDER", "It's OK")
            }
        }
    }

    fun getStoresAddresses() {
        viewModelScope.launch {
            val callback = {status: String, result: List<Store> ->
                if(status == "SUCCESS") {
                    _uiState.update { currentState ->
                        currentState.copy(
                            stores = result
                        )
                    }
                }
            }
            ApiCalls.shared.getAllStoresApi(callback)
        }
    }


}