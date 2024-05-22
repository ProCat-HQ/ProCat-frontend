package com.example.procatfirst.ui.courier.orders

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
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.MapActivity
import com.example.procatfirst.R
import com.example.procatfirst.data.Delivery
import com.example.procatfirst.data.DeliveryOrder
import com.example.procatfirst.data.Order
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.setOrderStatus
import com.example.procatfirst.ui.managment.delivery.AdminDeliveryUiState
import com.example.procatfirst.ui.theme.ProCatFirstTheme



@Composable
fun CourierOrdersScreen(
        controller : Context,
        courierOrdersViewModel: CourierOrdersViewModel = viewModel()
) {
    val deliveriesUiState by courierOrdersViewModel.uiState.collectAsState()
    var currentOrder by remember { mutableStateOf<DeliveryOrder?>(null) }



    //val orders by courierOrdersViewModel.orders.collectAsState()
    var changeStatusDialogVisible by remember { mutableStateOf(false) }


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

        deliveriesUiState.deliveries.forEach { delivery ->
            if (delivery.order.status != "closed") {
                DeliveryCard(
                    delivery = delivery,
                    changeStatus = {
                        changeStatusDialogVisible = true
                        currentOrder = delivery.order
                    },
                    deliveriesUiState
                )
            }
        }
        OutlinedButton(
            onClick = { courierOrdersViewModel.createRoute() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Создать маршрут")
        }

        if (deliveriesUiState.mapButtonVisible) {
            Button(
                onClick = { controller.startActivity(Intent(controller, MapActivity().javaClass)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text("Показать карту")
            }
        }

        if (changeStatusDialogVisible) {
            ChangeStatusDialog(
                currentOrder = currentOrder!!,
                onDismiss = { changeStatusDialogVisible = false },
                onChangeStatus = { newStatus ->
                    courierOrdersViewModel.changeOrderStatus(currentOrder!!.id, newStatus)
                    changeStatusDialogVisible = false
                }
            )
        }
    }
}

@Composable
fun DeliveryCard(
    delivery: Delivery,
    changeStatus: (DeliveryOrder) -> Unit,
    deliveryUiState: CourierOrdersUiState
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
                    text = "Доставка ${delivery.id}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Время ${delivery.timeStart} - ${delivery.timeEnd}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Метод доставки: ${delivery.method}",
                    style = MaterialTheme.typography.bodyLarge
                )

                if (expanded) {
                    Text(
                        text = "Заказ ${delivery.order.id}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Адрес ${delivery.order.address}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Сумма заказа ${delivery.order.totalPrice}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }

            TextButton(
                onClick = { changeStatus(delivery.order) },
                modifier = Modifier
                    .weight(2f)
                    .padding(4.dp)
            ) {
                Text(
                    text = delivery.order.status,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}


@Composable
fun ChangeStatusDialog(
    currentOrder: DeliveryOrder,
    onDismiss: () -> Unit,
    onChangeStatus: (String) -> Unit,
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Обновить стaтус заказа ${currentOrder.id}") },
        text = {
            Column {
                Text("Выберите новый статус для заказа")
            }
        },
        confirmButton = {
            if (currentOrder.status == "delivering") {
                Button(onClick = { onChangeStatus("rent") }) {
                    Text("В аренде",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Button(onClick = { onChangeStatus("returned") }) {
                    Text("Возвращен",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun ProfilePreview() {
//    ProCatFirstTheme {
//        CourierOrdersScreen()
//    }
//}