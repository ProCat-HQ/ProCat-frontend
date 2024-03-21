package com.example.procatfirst.ui.personal.orders

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.MapActivity
import com.example.procatfirst.R

@Composable
fun OrdersDeliveryScreen(
    controller : Context,
    ordersViewModel: OrdersViewModel = viewModel()
) {
    val orders by ordersViewModel.deliveryOrders.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.list_of_orders),
            style = MaterialTheme.typography.titleLarge,
        )

        orders.forEach { order ->
            orderItem(
                orderId = order.orderId,
                status = order.status,
                totalPrice = order.totalPrice,
                rentalPeriod = order.rentalPeriod,
                address = order.address,
            )
        }

        Button(
            onClick = { controller.startActivity(Intent(controller, MapActivity().javaClass)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Показать карту")
        }

    }
}



