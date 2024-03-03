package com.example.procatfirst.ui.personal.orders

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.ui.personal.notifications.notification
import com.example.procatfirst.ui.theme.ProCatFirstTheme

@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel = viewModel()
) {
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
                orderId = order.orderId,
                status = order.status,
                totalPrice = order.totalPrice,
                rentalPeriod = order.rentalPeriod,
                address = order.address,
            )
        }
    }
}

@Composable
fun orderItem(
    orderId: Int,
    status: String,
    totalPrice: Int,
    rentalPeriod: String,
    address: String,
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
            .clickable { expanded = !expanded  }
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

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProCatFirstTheme {
        OrdersScreen()
    }
}