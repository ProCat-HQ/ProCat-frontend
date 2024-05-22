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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.procatfirst.MapActivity
import com.example.procatfirst.ProCatScreen
import com.example.procatfirst.R
import com.example.procatfirst.data.DeliveryOrder
import com.example.procatfirst.data.OrderFull
import com.example.procatfirst.data.OrderItem

@Composable
fun OrdersManagerScreen(
    controller : Context,
    ordersViewModel: OrdersManagerViewModel = viewModel(),
    navController: NavController
) {
    val ordersUiState by ordersViewModel.uiState.collectAsState()

    var changeStatusDialogVisible by remember { mutableStateOf(false) }
    var currentOrder by remember { mutableStateOf<OrderFull?>(null) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.list_of_orders),
            style = MaterialTheme.typography.titleLarge,
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            items(ordersUiState.orders) { orderFull ->
                if (orderFull.status != "closed") {
                    OrderItemFull(
                        orderFull = orderFull,
                        changeStatus = {
                            changeStatusDialogVisible = true
                            currentOrder = orderFull
                        },
                        onViewPaymentsClicked = { orderId ->
                            navController.navigate("${ProCatScreen.Payments.name}/$orderId")
                        }
                    )
                }
            }
        }

        Button(
            onClick = { controller.startActivity(Intent(controller, MapActivity().javaClass)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Показать карту")
        }
        if (changeStatusDialogVisible) {
            ChangeStatusDialog(
                currentOrder = currentOrder!!,
                onDismiss = { changeStatusDialogVisible = false },
                onChangeStatus = { newStatus ->
                    ordersViewModel.changeOrderStatus(currentOrder!!.id, newStatus)
                    changeStatusDialogVisible = false
                }
            )
        }
    }
}

@Composable
fun OrderItemFull(
    changeStatus: (OrderFull) -> Unit,
    orderFull : OrderFull,
    onViewPaymentsClicked: (Int) -> Unit
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
                    text = "Заказ ${orderFull.id}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Адрес ${orderFull.address}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Компания: ${orderFull.companyName}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Депозит ${orderFull.deposit}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Период доставки: ${orderFull.rentalPeriodStart} - ${orderFull.rentalPeriodEnd}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Статус: ${orderFull.status}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Итог: ${orderFull.totalPrice}",
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlinedButton(
                    onClick = { onViewPaymentsClicked(orderFull.id) },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Посмотреть платежи",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                if (expanded && orderFull.items != null) {
                    orderFull.items.forEach { item ->
                        OrderItemCard(
                            orderItem = item
                        )
                    }
                }

            }
            TextButton(
                onClick = { changeStatus(orderFull) },
                modifier = Modifier
                    .weight(2f)
                    .padding(4.dp)
            ) {
                Text(
                    text = orderFull.status,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Composable
fun OrderItemCard(
    orderItem: OrderItem
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .padding(vertical = 8.dp),
            //.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),

        ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Id: ${orderItem.id}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Название: ${orderItem.name}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Количество: ${orderItem.count}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Цена: ${orderItem.price}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}



@Composable
fun ChangeStatusDialog(
    currentOrder: OrderFull,
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
        /*
        confirmButton = {
            LazyColumn {
                items(items = listOf(
                    "awaitingPayment", "accepted", "readyToPickup", "readyToDelivery", "delivering",
                    "rent", "shouldBeReturned", "expired", "problem", "deliveryBack", "returned",
                    "itemsCheck", "awaitingRepairPayment", "closed"
                )) { status ->
                    Button(
                        onClick = { onChangeStatus(status) },
                        //modifier = Modifier
                        //    .size(width = 200.dp, height = 40.dp)
                        //    .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = status,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }, */

        confirmButton = {
            if (currentOrder.status == "accepted") {
                Button(onClick = { onChangeStatus("readyToPickup") }) {
                    Text("Готов к выдаче",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            if (currentOrder.status == "awaitingRejection" || currentOrder.status == "awaitingMoneyBack") {
                Button(onClick = { onChangeStatus("rejected") }) {
                    Text("Отменен",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Button(onClick = { onChangeStatus("closed") }) {
                Text("Закрыт",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            /*
            Button(onClick = { onChangeStatus("awaitingPayment") },
            ) {
                Text("Ожидание оплаты",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("accepted") }) {
                Text("Принято в обработку",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("awaitingRejection") }) {
                Text("Запрос об отмене",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("awaitingMoneyBack") }) {
                Text("Возврат денег",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("rejected") }) {
                Text("Отменен",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("readyToPickup") }) {
                Text("Готов к выдаче",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("readyToDelivery") }) {
                Text("В ожидании доставки",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("delivering") }) {
                Text("Доставляется",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("rent") }) {
                Text("В аренде",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("shouldBeReturned") }) {
                Text("Истекает срок аренды",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            /*
            Button(onClick = { onChangeStatus("expired") }) {
                Text("Просрочено",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(onClick = { onChangeStatus("extensionRequest") }) {
                Text("Запрос на продление",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("extended") }) {
                Text("Продлено",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(onClick = { onChangeStatus("problem") }) {
                Text("Проблема с инструментом",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("deliveryBack") }) {
                Text("Доставка обратно",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("returned") }) {
                Text("Возвращен",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("itemsCheck") }) {
                Text("Поверка инструмента",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = { onChangeStatus("awaitingRepairPayment") }) {
                Text("Ожидание оплаты за ремонт",
                    style = MaterialTheme.typography.bodySmall
                )
            } */
            Button(onClick = { onChangeStatus("closed") }) {
                Text("Закрыт",
                    style = MaterialTheme.typography.bodySmall
                )
            } */

        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}



