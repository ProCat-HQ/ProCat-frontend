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
import com.example.procatfirst.data.OrderSmall
import com.example.procatfirst.data.Store
import com.example.procatfirst.data.User
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.api.StoreApiCalls
import com.example.procatfirst.repository.cache.UserOrdersCache
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class OrderingViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(OrderingUiState())
    val uiState: StateFlow<OrderingUiState> = _uiState.asStateFlow()

    var country by mutableStateOf("")
    var city by mutableStateOf("")
    var address by mutableStateOf("")

    var delivery by mutableStateOf(true)

    var orderStatus by mutableStateOf("В обработке")

    init{
        open()
        getStoresAddresses()
        _uiState.value = _uiState.value.copy(selectedDate = getTomorrowDate())
    }

    private fun open(){
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _uiState.value = OrderingUiState(tools = DataCoordinator.shared.getUserCart().values.toList())
            }
        }
    }

    private fun getTomorrowDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun updateSelectedPeriod(selectedPeriod: Int) {
        _uiState.value = _uiState.value.copy(periodInDays = selectedPeriod)
    }

    fun updateSelectedDate(selectedDate: String) {
        _uiState.value = _uiState.value.copy(selectedDate = selectedDate)
    }

    fun updateSelectedTimeFrom(selectedTimeFromHour: Int, selectedTimeFromMinute: Int) {
        _uiState.value = _uiState.value.copy(selectedTimeFromHour = selectedTimeFromHour, selectedTimeFromMinute = selectedTimeFromMinute)
        validateEndTime()

    }

    fun updateSelectedTimeTo(selectedTimeToHour: Int, selectedTimeToMinute: Int) {
        if (isValidEndTime(selectedTimeToHour, selectedTimeToMinute)) {
            _uiState.value = _uiState.value.copy(
                selectedTimeToHour = selectedTimeToHour,
                selectedTimeToMinute = selectedTimeToMinute
            )
        }
    }

    private fun isValidEndTime(endHour: Int, endMinute: Int): Boolean {
        val startMinutes = _uiState.value.selectedTimeFromHour * 60 + _uiState.value.selectedTimeFromMinute
        val endMinutes = endHour * 60 + endMinute
        return (endMinutes - startMinutes) >= 120
    }

    private fun validateEndTime() {
        val startMinutes = _uiState.value.selectedTimeFromHour * 60 + _uiState.value.selectedTimeFromMinute
        val endMinutes = _uiState.value.selectedTimeToHour * 60 + _uiState.value.selectedTimeToMinute
        if ((endMinutes - startMinutes) < 120) {
            // Adjust end time to be at least two hours after start time
            val newEndTimeMinutes = startMinutes + 120
            _uiState.update { currentState ->
                currentState.copy(
                    selectedTimeToHour = newEndTimeMinutes / 60,
                    selectedTimeToMinute = newEndTimeMinutes % 60
                )
            }
        }
    }

    private fun updateSelectedAddress(address: String) {
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

            DataCoordinator.shared.createNewOrder(
                OrderRequest(deliveryTimeFrom,
                    "$dateEnd 00:00:00", order.address, null, type, deliveryTimeFrom, deliveryTimeTo)) {
                if (it == null) {
                    Toast.makeText(context, "Ошибка при оформлении заказа", Toast.LENGTH_SHORT).show()
                    Log.e("NEW ORDER", "It's null!")
                }
                else {
                    //TODO show result
                    UserOrdersCache.shared.setOrderResponse(it)
                    Log.i("NEW ORDER", "It's OK")
                    nextPage(this@OrderingViewModel)
                }
            }

        }
    }

    private fun getStoresAddresses() {
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
            StoreApiCalls.shared.getAllStoresApi(callback)
        }
    }

    fun getOrderResponse() : OrderSmall? {
        return UserOrdersCache.shared.getOrderResponse()
    }

}