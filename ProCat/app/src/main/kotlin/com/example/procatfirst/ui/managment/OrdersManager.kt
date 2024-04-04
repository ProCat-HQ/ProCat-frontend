package com.example.procatfirst.ui.managment

import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.MapActivity
import com.example.procatfirst.R
import com.example.procatfirst.data.Order
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.setOrderStatus

var currentOrder : Order? = null

@Composable
fun OrdersManagerScreen(
    controller : Context,
    ordersViewModel: OrdersViewModel = viewModel()
) {
    var changeStatusDialogVisible by remember { mutableStateOf(false) }
    val orders by ordersViewModel.orders.collectAsState()

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
                {changeStatusDialogVisible = true},
                order
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
    if (changeStatusDialogVisible) {
        ChangeStatusDialog(onDismiss = { changeStatusDialogVisible = false }, onChangeStatus = {status ->
            currentOrder?.let {
                DataCoordinator.shared.setOrderStatus(
                    it, status)
            }
            changeStatusDialogVisible = false
        })
    }
}

@Composable
fun orderItem(
    changeStatus : () -> Unit,
    order : Order,
    orderId : Int = order.orderId,
    rentalPeriod : String = order.rentalPeriod,
    status : String = order.status,
    address : String = order.address,
    totalPrice : Int = order.totalPrice,
    ){
    var expanded by rememberSaveable { mutableStateOf(false) }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.background)
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(8.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .weight(3f)
            ) {
                Text(
                    text = "Заказ $orderId",
                    style = MaterialTheme.typography.bodyLarge
                )
                Row(

                ){
                    Text(
                        text = rentalPeriod.substringAfterLast("-"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                }
                Text(
                    text = totalPrice.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )

                if (expanded) {
                    Text(
                        text = address,
                    )
                }

            }
            TextButton(
                onClick = { currentOrder = order; changeStatus() },
                modifier = Modifier
                    .weight(3f)
                    .padding(4.dp)
            ) {
                Text(
                    text = status,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}



@Composable
fun ChangeStatusDialog(
    onDismiss: () -> Unit,
    onChangeStatus: (String) -> Unit,
    ) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Обновить стaтус заказа") },
        text = {
            Column {

            }
        },
        confirmButton = {
            Button(onClick = { onChangeStatus("Принят в обработку") }) {
                Text("Принят в обработку")
            }
            Button(onClick = { onChangeStatus("Ожидает доставки") }) {
                Text("Ожидает доставки")
            }
            Button(onClick = { onChangeStatus("Ожидает возврата") }) {
                Text("Ожидает возврата")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}



