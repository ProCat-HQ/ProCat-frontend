package com.example.procatfirst.ui.ordering

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.procatfirst.R

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
            text = "Произведите оплату по указанному коду. После подтверждения менеджером, заказ поступит в сборку"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Статус заказа: ${orderingViewModel.orderStatus}")
    }
}