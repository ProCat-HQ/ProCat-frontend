package com.example.procatfirst.ui.managment.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.Payment
import com.example.procatfirst.repository.api.ApiCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(PaymentsUiState(listOf()))
    val uiState: StateFlow<PaymentsUiState> = _uiState.asStateFlow()

    fun loadPayments(orderId: Int) {
        viewModelScope.launch {
            val callback = {status: String, result: List<Payment> ->
                if (status == "SUCCESS" && result != null) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            payments = result
                        )
                    }
                }
            }
            ApiCalls.shared.getInfoAboutPaymentsApi(orderId, callback)
        }
    }

    fun addPayment(id: Int, method: String, amount: Int) {
        viewModelScope.launch {
            val callback = {status: String ->
                if (status == "SUCCESS") {
                    loadPayments(id)
                }
            }
            ApiCalls.shared.updatePaymentInfoApi(id, amount, method, callback)
        }
    }
}
