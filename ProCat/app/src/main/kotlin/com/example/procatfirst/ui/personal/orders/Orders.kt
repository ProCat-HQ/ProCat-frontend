package com.example.procatfirst.ui.personal.orders

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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel = viewModel()
) {
    val ordersUiState by ordersViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (ordersUiState.orders.isEmpty()) {
            Text(
                text = stringResource(R.string.your_orderlist_is_empty),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        else {
            ordersUiState.orders.forEach { order ->
                OrderItem(
                    orderId = order.id,
                    status = order.status,
                    totalPrice = order.totalPrice,
                    rentalPeriod = LocalDate.parse(order.rentalPeriodStart.substringBefore('T')).format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    ).toString() + " - " + LocalDate.parse(order.rentalPeriodEnd.substringBefore('T')).format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString(),
                    address = order.address,
                    onCancelOrder = { ordersViewModel.cancelOrder(order.id) }
                )
            }
        }
    }
}

@Composable
fun OrderItem(
    orderId: Int,
    status: String,
    totalPrice: Int,
    rentalPeriod: String,
    address: String,
    onCancelOrder: () -> Unit
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
                .padding(6.dp)
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
                    .weight(4f)
            ) {
                Text(
                    text = "Заказ $orderId",
                    style = MaterialTheme.typography.bodyLarge
                )
                Row(

                ){
                    Text(
                        text = rentalPeriod,
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
                    TextButton(
                        onClick = { onCancelOrder() },
                    ) {
                        Text(
                            text = "Отменить заказ",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }

            }

            TextButton(
                onClick = {  },
                modifier = Modifier
                    .weight(2f)
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