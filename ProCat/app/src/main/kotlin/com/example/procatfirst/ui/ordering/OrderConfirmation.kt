package com.example.procatfirst.ui.ordering

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrderConfirmation(
    orderingViewModel: OrderingViewModel,
) {
    val orderingUiState by orderingViewModel.uiState.collectAsState()


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Произведите оплату по указанному коду или оплатите аренду при получении. Заказ скоро поступит в сборку"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Статус заказа: ${orderingViewModel.orderStatus}")
        val order = orderingViewModel.getOrderResponse()
        if (order != null) {
            Text(text = "Номер заказа: ${order.orderId}, стоимость: ${order.totalPrice}")
        }
    }
}