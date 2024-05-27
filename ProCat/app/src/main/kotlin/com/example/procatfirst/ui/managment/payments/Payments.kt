package com.example.procatfirst.ui.managment.payments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.data.Payment

@Composable
fun PaymentsScreen(
    orderId: Int,
    paymentsViewModel: PaymentsViewModel = viewModel()
){
    val paymentsUiState by paymentsViewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedPayment by remember { mutableStateOf<Payment?>(null) }

    Column (
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.payments) + " для заказа " + orderId,
            style = MaterialTheme.typography.titleLarge,
        )
        Button(
            onClick = { paymentsViewModel.loadPayments(orderId) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text("Загрузить платежи")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            //modifier = Modifier.padding(16.dp),
        ) {
            items(paymentsUiState.payments) { payment ->
                PaymentCard(
                    payment = payment,
                    onAddPaymentClick = {
                        selectedPayment = payment
                        showDialog = true
                    }
                )
            }
        }
    }
    if (showDialog && selectedPayment != null) {
        AddPaymentDialog(
            onDismiss = { showDialog = false },
            onConfirm = { method, amount ->
                paymentsViewModel.addPayment(selectedPayment!!.id, method, amount)
                showDialog = false
            }
        )
    }
}

@Composable
fun PaymentCard(
    payment: Payment,
    onAddPaymentClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f)
            ) {
                Text(
                    text = "Id: ${payment.id}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Метод: ${payment.method}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Цена: ${payment.price}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Внесено: ${payment.paid}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Button(
                modifier = Modifier
                    .weight(2f),
                onClick = onAddPaymentClick) {
                Text("Внести платеж")
            }
        }
    }
}


@Composable
fun AddPaymentDialog(
    onDismiss: () -> Unit,
    onConfirm: (method: String, amount: Int) -> Unit
) {
    var method by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.add_payment))
        },
        text = {
            Column {
                TextField(
                    value = method,
                    onValueChange = { method = it },
                    label = { Text(stringResource(R.string.payment_method)) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text(stringResource(R.string.payment_amount)) }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amountValue = amount.toIntOrNull()
                    if (amountValue != null) {
                        onConfirm(method, amountValue)
                    }
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.cancellation))
            }
        }
    )
}
