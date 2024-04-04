package com.example.procatfirst.ui.ordering

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.api.ApiCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderingViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(OrderingUiState())
    val uiState: StateFlow<OrderingUiState> = _uiState.asStateFlow()


    var address by mutableStateOf("")

    var delivery by mutableStateOf(true)

    var orderStatus by mutableStateOf("В ожидании подтверждения")



    fun updateSelectedDate(selectedDate: String) {
        _uiState.value = _uiState.value.copy(selectedDate = selectedDate)
    }

    fun updateSelectedTimeFrom(selectedTimeFromHour: Int, selectedTimeFromMinute: Int) {
        _uiState.value = _uiState.value.copy(selectedTimeFromHour = selectedTimeFromHour, selectedTimeFromMinute = selectedTimeFromMinute)
    }

    fun updateSelectedTimeTo(selectedTimeToHour: Int, selectedTimeToMinute: Int) {
        _uiState.value = _uiState.value.copy(selectedTimeToHour = selectedTimeToHour, selectedTimeToMinute = selectedTimeToMinute)
    }

    fun updateSelectedAddress(address: String) {
        _uiState.value = _uiState.value.copy(address = address)
    }

    fun order() {
        // send request to make order with date, time, address and items!
        updateSelectedAddress(address)
        viewModelScope.launch {
            //ApiCalls.shared.postCart()
            //ApiCalls.shared.postOrder()
        }
    }


}