package com.example.procatfirst.ui.ordering

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.procatfirst.R

@Composable
fun OrderConfirmation(
    orderingViewModel: OrderingViewModel,
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.order_confirmation_text)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Статус заказа: ${orderingViewModel.orderStatus}")
        val order = orderingViewModel.getOrderResponse()
        if (order != null) {
            Text(text = "Номер заказа: ${order.orderId}, стоимость: ${order.totalPrice}")
        }
        Image(
            painter = painterResource(id = R.drawable.code),
            contentDescription = stringResource(id = R.string.logo),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f)
                .padding(top = 5.dp, bottom = 5.dp)
        )
    }
}